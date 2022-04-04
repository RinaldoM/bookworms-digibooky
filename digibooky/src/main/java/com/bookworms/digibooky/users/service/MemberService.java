package com.bookworms.digibooky.users.service;

import com.bookworms.digibooky.users.api.CreateMemberDto;
import com.bookworms.digibooky.users.api.dto.MemberDto;
import com.bookworms.digibooky.users.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

@Service
public class MemberService {

    Logger serviceLogger = LoggerFactory.getLogger(MemberService.class);
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;

    public MemberService(MemberMapper memberMapper, MemberRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
    }

    public MemberDto registerMember(CreateMemberDto createMemberDto) {
        if (!isEmailValid(createMemberDto.getEmail())) {
            serviceLogger.error("Not a proper email address to register a member !");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        serviceLogger.info("Proper email address introduced.");

        Member member = memberMapper.toMember(createMemberDto);
        serviceLogger.info("Member getting registered.");
        memberRepository.saveMember(member);
        return memberMapper.toDto(member);
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }
}
