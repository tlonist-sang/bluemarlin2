package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.annotation.LoginUser;
import com.project.bluemarlin2.bluemarlin2.domain.CustomResponse;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v1/setting")
@RequiredArgsConstructor
public class UserSettingController {
    private final MemberRepository memberRepository;

    @GetMapping
    public CustomResponse getUserSetting(@LoginUser Member member){
        return null;
    }


    @PostMapping
    public CustomResponse updateUserSetting(@LoginUser Member member){
        return null;
    }
}
