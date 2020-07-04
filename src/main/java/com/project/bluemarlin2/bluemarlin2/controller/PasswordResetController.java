package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.constants.ErrorCode;
import com.project.bluemarlin2.bluemarlin2.domain.CustomResponse;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping (value="/password")
public class PasswordResetController {

    private final MemberRepository memberRepository;

    @PostMapping(value="/token")
    public CustomResponse getPasswordResetToken(@RequestBody PasswordResetDto passwordResetDto){
        Optional<Member> byUserId = memberRepository.findByUserId(passwordResetDto.getUserId());
        if(byUserId.isPresent()){
            Member member = byUserId.get();
            if(member.getEmail().equals(passwordResetDto.getEmail())){
                //send email
            }
        }
        return new CustomResponse(ApiConstants.SUCCESS);
    }

    @PostMapping(value="/verify")
    public CustomResponse validateToken(@RequestBody ResetPasswordToken resetPasswordToken){
        if(checkResetPasswordToken(resetPasswordToken.token)){
            return new CustomResponse(ApiConstants.SUCCESS);
        }
        return new CustomResponse(ApiConstants.FAIL);
    }

    @PostMapping(value="/reset")
    public CustomResponse resetPassword(@RequestBody ResetPasswordInfo resetPasswordInfo){
        if(!resetPasswordInfo.getPassword().equals(resetPasswordInfo.getPasswordConfirm())){
            return new CustomResponse(ApiConstants.FAIL, ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }
        Optional<Member> byUserId = memberRepository.findByUserId(resetPasswordInfo.getUserId());
        if(byUserId.isPresent()){
            Member member = byUserId.get();
            member.setPassword(resetPasswordInfo.getPassword());
            memberRepository.encodeAndSave(member);
            return new CustomResponse(ApiConstants.SUCCESS);
        }
        return new CustomResponse(ApiConstants.FAIL);
    }

    private boolean checkResetPasswordToken(String token){
        return false;
    }

    @Data
    static class PasswordResetDto{
        String userId;
        String email;
    }

    @Data
    static class ResetPasswordToken{
        String token;
    }

    @Data
    static class ResetPasswordInfo{
        String userId;
        String password;
        String passwordConfirm;
    }
}
