package com.bookworms.digibooky.user.api;

import com.bookworms.digibooky.user.api.dto.CreateMemberDto;
import com.bookworms.digibooky.user.api.dto.LibrarianDto;
import com.bookworms.digibooky.user.api.dto.MemberDto;
import com.bookworms.digibooky.user.domain.Member;
import com.bookworms.digibooky.user.service.UserMapper;
import com.bookworms.digibooky.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping()
public class UserController {

    private final UserService userService;

    public UserController(UserService memberService) {
        this.userService = memberService;
    }

    @PostMapping(path = "members", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerMember(@RequestBody CreateMemberDto createMemberDto){
        return userService.registerMember(createMemberDto);
    }

    @PostMapping(path = "librarians", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public LibrarianDto registerLibrarian(@RequestBody LibrarianDto librarianDto){
        return userService.registerLibrarian(librarianDto);
    }

    @GetMapping(path = "members", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<MemberDto> viewMembers(){
        return userService.viewMembers();
    }
}
