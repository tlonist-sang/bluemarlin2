package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.domain.CustomResponse;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/name")
public class NameCheckController {
    private final MemberRepository memberRepository;

    @GetMapping
    public CustomResponse checkNameExist(@RequestParam String userId){
        Optional<Member> byUserId = memberRepository.findByUserId(userId);
        if(byUserId.isPresent()){
            return new CustomResponse(ApiConstants.SUCCESS);
        }
        return new CustomResponse(ApiConstants.FAIL);
    }
}
