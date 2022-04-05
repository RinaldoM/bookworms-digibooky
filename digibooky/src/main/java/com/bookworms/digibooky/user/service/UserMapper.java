package com.bookworms.digibooky.user.service;

import com.bookworms.digibooky.user.api.dto.CreateMemberDto;
import com.bookworms.digibooky.user.api.dto.LibrarianDto;
import com.bookworms.digibooky.user.api.dto.MemberDto;
import com.bookworms.digibooky.user.domain.Librarian;
import com.bookworms.digibooky.user.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public MemberDto toMemberDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getStreetName(),
                member.getStreetNumber(),
                member.getPostalCode(),
                member.getCity());
    }


    public Member toMember(CreateMemberDto createMemberDto) {
        return new Member(
                createMemberDto.getInss(),
                createMemberDto.getLastName(),
                createMemberDto.getFirstName(),
                createMemberDto.getEmail(),
                createMemberDto.getStreetName(),
                createMemberDto.getStreetNumber(),
                createMemberDto.getPostalCode(),
                createMemberDto.getCity());
    }

    public LibrarianDto toLibrarianDto(Librarian librarian) {
        return new LibrarianDto(
                librarian.getId(),
                librarian.getLastName(),
                librarian.getFirstName(),
                librarian.getEmail());
    }

    public Librarian toLibrarian(LibrarianDto librarianDto) {
        return new Librarian(
                librarianDto.getLastName(),
                librarianDto.getFirstName(),
                librarianDto.getEmail()
        );
    }

    public Collection<MemberDto> toMemberDto(Collection<Member> allMembers) {
        return allMembers.stream()
                .map(this::toMemberDto)
                .collect(Collectors.toList());
    }
}
