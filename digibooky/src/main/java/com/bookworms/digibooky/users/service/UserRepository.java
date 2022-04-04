package com.bookworms.digibooky.users.service;

import com.bookworms.digibooky.users.api.dto.LibrarianDto;
import com.bookworms.digibooky.users.domain.Librarian;
import com.bookworms.digibooky.users.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
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
}
