package com.bookworms.digibooky.users.service;

import com.bookworms.digibooky.users.api.dto.CreateMemberDto;
import com.bookworms.digibooky.users.api.dto.MemberDto;
import com.bookworms.digibooky.users.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

@Service
public class UserService {

    Logger serviceLogger = LoggerFactory.getLogger(UserService.class);
    private final UserMapper memberMapper;
    private final UserRepository memberRepository;

    public UserService(UserMapper memberMapper, UserRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
    }

    public MemberDto registerMember(CreateMemberDto createMemberDto) {
        emailValidation(createMemberDto);
        inssValidation(createMemberDto);
        validateAndCheckLoggingMessage(isEmptyNullSafe(createMemberDto.getLastName()), "No last name filled in to register a member !", "Proper last name introduced.");
        validateAndCheckLoggingMessage(isEmptyNullSafe(createMemberDto.getCity()), "No city filled in to register a member !", "Proper city introduced.");

        Member member = memberMapper.toMember(createMemberDto);
        serviceLogger.info("Member getting registered.");
        memberRepository.saveMember(member);
        return memberMapper.toMemberDto(member);
    }

    private void validateAndCheckLoggingMessage(boolean condition, String errorMessage, String confirmationMessage) {
        if (condition) {
            serviceLogger.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        serviceLogger.info(confirmationMessage);
    }

    private boolean isEmptyNullSafe(String string) {
        return string == null || string.isEmpty();
    }

    private void emailValidation(CreateMemberDto createMemberDto) {
        validateAndCheckLoggingMessage(!isEmailFormValid(createMemberDto.getEmail()), "Not a proper email address to register a member !", "Proper email address introduced.");
        validateAndCheckLoggingMessage(memberRepository.emailAlreadyExists(createMemberDto.getEmail()), "Please provide another email address to register a member, this one is already used !", "New email address introduced.");
    }

    private boolean isEmailFormValid(String email) {
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

    private void inssValidation(CreateMemberDto createMemberDto) {
        validateAndCheckLoggingMessage(isEmptyNullSafe(createMemberDto.getInss()), "No inss filled in to register a member !", "Proper inss number introduced.");
        validateAndCheckLoggingMessage(memberRepository.inssAlreadyExists(createMemberDto.getInss()), "Please provide another inss number to register a member, this one is already used !", "New inss number introduced.");
    }
}
