package com.bookworms.digibooky.users.service;

import com.bookworms.digibooky.book.domain.Book;
import com.bookworms.digibooky.book.domain.BookRepository;
import com.bookworms.digibooky.users.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberRepository {
    private final Map<String, Member> membersById;
    private final Logger repositoryLogger = LoggerFactory.getLogger(BookRepository.class);


    public MemberRepository() {
        this.membersById = new HashMap<>();
    }

    public void saveMember(Member memberToSave) {
        membersById.put(memberToSave.getId(), memberToSave);
    }

    public boolean emailAlreadyExists(String email) {
        return membersById.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }

    public Member getMemberById(String memberId) {
        Member foundMember = membersById.get(memberId);
        if (foundMember == null) {
            repositoryLogger.error("No member found for member ID " + memberId);
            throw new IllegalArgumentException("No member found for member ID " + memberId);
        }
        return foundMember;
    }
}
