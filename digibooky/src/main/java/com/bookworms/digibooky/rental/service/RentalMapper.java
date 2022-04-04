package com.bookworms.digibooky.rental.service;

import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.book.domain.BookRepository;
import com.bookworms.digibooky.rental.api.dto.CreateRentalDto;
import com.bookworms.digibooky.rental.domain.Rental;
import com.bookworms.digibooky.users.domain.Member;
import com.bookworms.digibooky.users.service.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {
    private BookRepository bookRepository;
    private MemberRepository memberRepository;

    public RentalMapper(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public Rental toRental(CreateRentalDto createRental){
        Book book = bookRepository.getBookByIsbn(createRental.getBookIsbn());
        Member member = memberRepository.getMemberById(createRental.getUserId());
        return new Rental(book,member);
    }

}
