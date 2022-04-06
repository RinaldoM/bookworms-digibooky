package com.bookworms.digibooky.user.api;

import com.bookworms.digibooky.security.Feature;
import com.bookworms.digibooky.security.SecurityService;
import com.bookworms.digibooky.user.api.dto.CreateLibrarianDto;
import com.bookworms.digibooky.user.api.dto.CreateMemberDto;
import com.bookworms.digibooky.user.api.dto.LibrarianDto;
import com.bookworms.digibooky.user.api.dto.MemberDto;
import com.bookworms.digibooky.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.bookworms.digibooky.security.Feature.REGISTER_LIBRARIAN;

@RestController
@RequestMapping()
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService memberService, SecurityService securityService) {
        this.userService = memberService;
        this.securityService = securityService;
    }

    @PostMapping(path = "members", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerMember(@RequestBody CreateMemberDto createMemberDto){
        return userService.registerMember(createMemberDto);
    }

    @PostMapping(path = "librarians", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public LibrarianDto registerLibrarian(@RequestHeader String authorizationId, @RequestBody CreateLibrarianDto createLibrarianDto){
        securityService.validateAuthorization(authorizationId, REGISTER_LIBRARIAN);
        return userService.registerLibrarian(createLibrarianDto);
    }

    @GetMapping(path = "members", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<MemberDto> viewMembers(){
        return userService.viewMembers();
    }
}
