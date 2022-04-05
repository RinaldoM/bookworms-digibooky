package com.bookworms.digibooky.rental.service;

import com.bookworms.digibooky.rental.api.dto.CreateRentalDto;
import com.bookworms.digibooky.rental.api.dto.RentalDto;
import com.bookworms.digibooky.rental.domain.Rental;
import com.bookworms.digibooky.rental.domain.RentalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final Logger serviceLogger = LoggerFactory.getLogger(RentalService.class);

    public RentalService(RentalRepository rentalRepository, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
    }

    public CreateRentalDto lendABook(CreateRentalDto createRental) {
        Rental rental = rentalMapper.toRental(createRental);
        checkBookCondition(rental.getBook().getRentedState(), "The book is already rented out!");
        checkBookCondition(!rental.getBook().isActive(), "The book is not in the library!");
        rentalRepository.addRental(rental);
        rental.getBook().changeRentedState();
        serviceLogger.info("The book " + rental.getBook().getTitle() + " is rented to " + rental.getMember().getFirstName() + " " + rental.getMember().getLastName() + ".");
        return createRental;
    }

    private void checkBookCondition(boolean condition, String message) {
        if (condition) {
            serviceLogger.error(message);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public RentalDto returnRental(String rentalId) {
        Rental rental = rentalRepository.getRentalById(rentalId);
        rental.getBook().changeRentedState();
        rentalRepository.removeRental(rental);
        serviceLogger.info("The book " + rental.getBook().getTitle() + " is returned by " + rental.getMember().getFirstName() + " " + rental.getMember().getLastName() + ".");
        return rentalMapper.toDto(rental);
    }


    private List<RentalDto> filterRentalsOfMember(String memberId) {
        return rentalRepository.getAll().stream()
                .filter(rental -> rental.getMember().getId().equals(memberId))
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RentalDto> getRentalsOfMember(String memberId) {
        if (filterRentalsOfMember(memberId) == null || filterRentalsOfMember(memberId).size() == 0) {
            serviceLogger.error("No rentals found for member with id " + memberId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return filterRentalsOfMember(memberId);
    }
}
