package com.bookworms.digibooky.rental.domain;

import com.bookworms.digibooky.book.domain.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RentalRepository {
    private final Logger repositoryLogger = LoggerFactory.getLogger(BookRepository.class);

    private final Map<String, Rental> rentalsById;

    public RentalRepository() {
        this.rentalsById = new HashMap<>();
    }

    public void addRental(Rental rental) {
        rentalsById.put(rental.getRentalId(), rental);
    }

    public Rental getRentalById(String rentalId) {
        Rental foundRental = rentalsById.get(rentalId);
        if(foundRental == null){
            repositoryLogger.error("No rental could be found for id " + rentalId);
            throw new IllegalArgumentException("No rental could be found for id " + rentalId);
        }
        return foundRental ;
    }

    public void removeRental(Rental rental) {
        rentalsById.remove(rental.getRentalId());
    }

}
