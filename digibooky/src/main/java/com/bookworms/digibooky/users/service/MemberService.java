package com.bookworms.digibooky.users.service;

import com.bookworms.digibooky.users.api.CreateMemberDto;
import com.bookworms.digibooky.users.api.dto.MemberDto;
import com.bookworms.digibooky.users.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;

    public MemberService(MemberMapper memberMapper, MemberRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
    }

    public MemberDto registerMember(CreateMemberDto createMemberDto) {
        Member member = memberMapper.toMember(createMemberDto);
        memberRepository.saveMember(member);
        return memberMapper.toDto(member);
    }
}
