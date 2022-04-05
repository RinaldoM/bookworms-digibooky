package com.bookworms.digibooky.user.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Logger repositoryLogger = LoggerFactory.getLogger(UserRepository.class);

    private final Map<String, Member> membersById;
    private final Map<String, Librarian> librariansById;

    public UserRepository() {
        this.membersById = new HashMap<>();
        this.librariansById = new HashMap<>();
    }

    public void saveMember(Member memberToSave) {
        membersById.put(memberToSave.getId(), memberToSave);
    }

    public void saveLibrarian(Librarian librarianToSave) {
        librariansById.put(librarianToSave.getId(), librarianToSave);
    }

    public boolean emailAlreadyExists(String email) {
        return membersById.values().stream()
                .anyMatch(member -> member.getEmail().equalsIgnoreCase(email));
    }

    public boolean inssAlreadyExists(String inss) {
        return membersById.values().stream()
                .anyMatch(member -> member.getInss().equals(inss));
    }

    public Member getMemberById(String memberId) {
        Member foundMember = membersById.get(memberId);
        if (foundMember == null) {
            repositoryLogger.error("No member found for member ID " + memberId);
            throw new IllegalArgumentException("No member found for member ID " + memberId);
        }
        return foundMember;
    }

    public Collection<Member> getAllMembers() {
        return membersById.values();
    }
}
