package com.bookworms.digibooky.user.api;

import com.bookworms.digibooky.user.api.dto.CreateMemberDto;
import com.bookworms.digibooky.user.api.dto.LibrarianDto;
import com.bookworms.digibooky.user.api.dto.MemberDto;
import com.bookworms.digibooky.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class UserController {

    private final UserService userService;

    public UserController(UserService memberService) {
        this.userService = memberService;
    }

    @PostMapping(path = "members", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerMember(@RequestBody CreateMemberDto createMemberDto){
        return userService.registerMember(createMemberDto);
    }

    @PostMapping(path = "librarians", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public LibrarianDto registerLibrarian(@RequestBody LibrarianDto librarianDto){
        return userService.registerLibrarian(librarianDto);
    }
}
