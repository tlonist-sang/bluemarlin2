package com.project.bluemarlin2.bluemarlin2.controller;


import com.project.bluemarlin2.bluemarlin2.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/token")
@RequiredArgsConstructor
public class TokenApiController {
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping(value="/access-token/new")
    public String renewAccessToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return jwtTokenProvider.createAccessToken(authentication);
    }
}
