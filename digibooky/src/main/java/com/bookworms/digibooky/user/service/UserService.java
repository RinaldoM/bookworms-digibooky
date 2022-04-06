package com.bookworms.digibooky.user.service;

import com.bookworms.digibooky.user.api.dto.CreateLibrarianDto;
import com.bookworms.digibooky.user.api.dto.CreateMemberDto;
import com.bookworms.digibooky.user.api.dto.LibrarianDto;
import com.bookworms.digibooky.user.api.dto.MemberDto;
import com.bookworms.digibooky.user.domain.Librarian;
import com.bookworms.digibooky.user.domain.Member;
import com.bookworms.digibooky.user.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.regex.Pattern;

@Service
public class UserService {

    Logger serviceLogger = LoggerFactory.getLogger(UserService.class);
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public LibrarianDto registerLibrarian(CreateLibrarianDto createLibrarianDto) {
        Librarian librarian = userMapper.toLibrarian(createLibrarianDto);
        serviceLogger.info("Librarian getting created.");
        userRepository.saveLibrarian(librarian);
        return userMapper.toLibrarianDto(librarian);

    }

    public MemberDto registerMember(CreateMemberDto createMemberDto) {
        emailValidation(createMemberDto);
        inssValidation(createMemberDto);
        validateAndCheckLoggingMessage(isEmptyNullSafe(createMemberDto.getLastName()), "No last name filled in to register a member !", "Proper last name introduced.");
        validateAndCheckLoggingMessage(isEmptyNullSafe(createMemberDto.getCity()), "No city filled in to register a member !", "Proper city introduced.");

        Member member = userMapper.toMember(createMemberDto);
        serviceLogger.info("Member getting registered.");
        userRepository.saveMember(member);
        return userMapper.toMemberDto(member);
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
        validateAndCheckLoggingMessage(userRepository.emailAlreadyExists(createMemberDto.getEmail()), "Please provide another email address to register a member, this one is already used !", "New email address introduced.");
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
        validateAndCheckLoggingMessage(userRepository.inssAlreadyExists(createMemberDto.getInss()), "Please provide another inss number to register a member, this one is already used !", "New inss number introduced.");
    }

    public Collection<MemberDto> viewMembers() {
        return userMapper.toMemberDto(userRepository.getAllMembers());
    }
}
