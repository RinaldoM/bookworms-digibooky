package com.bookworms.digibooky.users.api;

import com.bookworms.digibooky.users.api.dto.MemberDto;
import com.bookworms.digibooky.users.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerMember(@RequestBody CreateMemberDto createMemberDto){
        return memberService.registerMember(createMemberDto);
    }

}
