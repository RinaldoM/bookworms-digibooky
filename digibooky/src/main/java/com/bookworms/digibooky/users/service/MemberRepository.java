package com.bookworms.digibooky.users.service;

import com.bookworms.digibooky.users.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberRepository {
    private final Map<String, Member> membersById;

    public MemberRepository() {
        this.membersById = new HashMap<>();
    }

    public void saveMember(Member memberToSave) {
        membersById.put(memberToSave.getId(), memberToSave);
    }
}
