package com.bookworms.digibooky.users.service;

import com.bookworms.digibooky.users.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<String, Member> membersById;

    public UserRepository() {
        this.membersById = new HashMap<>();
    }

    public void saveMember(Member memberToSave) {
        membersById.put(memberToSave.getId(), memberToSave);
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
