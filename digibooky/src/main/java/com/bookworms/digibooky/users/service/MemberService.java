package com.bookworms.digibooky.users.service;

import com.bookworms.digibooky.users.api.CreateMemberDto;
import com.bookworms.digibooky.users.api.dto.MemberDto;
import com.bookworms.digibooky.users.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberMapper memberMapper;
    private MemberRepository memberRepository;

    public MemberService(MemberMapper memberMapper, MemberRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
    }

    public MemberDto registerMember(CreateMemberDto createMemberDto) {
        Member createdMember = memberMapper.toMember(createMemberDto);
        memberRepository.saveMember(createdMember);
        return memberMapper.toDto(createdMember);
    }
}
