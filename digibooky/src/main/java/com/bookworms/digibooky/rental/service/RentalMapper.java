package com.bookworms.digibooky.rental.service;

import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.book.domain.BookRepository;
import com.bookworms.digibooky.rental.api.dto.CreateRentalDto;
import com.bookworms.digibooky.rental.api.dto.RentalDto;
import com.bookworms.digibooky.rental.domain.Rental;
import com.bookworms.digibooky.user.domain.Member;
import com.bookworms.digibooky.user.service.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public RentalMapper(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Rental toRental(CreateRentalDto createRental){
        Book book = bookRepository.getBookByIsbn(createRental.getBookIsbn());
        Member member = userRepository.getMemberById(createRental.getUserId());
        return new Rental(book,member);
    }

    public RentalDto toDto(Rental rental){
        return new RentalDto(rental.getRentalId(), rental.getBook().getTitle(),rental.getMember().getId());
    }

}
