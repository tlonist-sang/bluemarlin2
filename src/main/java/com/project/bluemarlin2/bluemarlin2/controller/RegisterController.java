package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.constants.ErrorCode;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.CustomResponse;
import com.project.bluemarlin2.bluemarlin2.domain.RoleType;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.exception.PasswordException;
import com.project.bluemarlin2.bluemarlin2.exception.UserAlreadyExistException;
import com.project.bluemarlin2.bluemarlin2.repository.MemberCustomRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/register")
@RequiredArgsConstructor
public class RegisterController {
    private final MemberCustomRepository memberCustomRepository;

    @PostMapping
    public CustomResponse registerUser(@RequestBody @Valid RegisterDto registerDto) {
        passwordValidationCheck(registerDto);
        userIdValidationCheck(registerDto);
        String result = register(registerDto);

        CustomResponse response = new CustomResponse()
                .setStatus(result)
                .setCount(1);
        return response;
    }

    private String register(RegisterDto registerDto) {
        Member member = new Member()
                .setUserId(registerDto.getUserId())
                .setEmail(registerDto.getEmail())
                .setPassword(registerDto.getPassword());

        List<UrlSource> urlSourceList = new ArrayList();
        UrlSource fox = new UrlSource().setUrl("www.foxnews.com").setMember(member);
        UrlSource bbc = new UrlSource().setUrl("www.bbc.com").setMember(member);
        UrlSource usaToday = new UrlSource().setUrl("www.usatoday.com").setMember(member);

        urlSourceList.add(fox);
        urlSourceList.add(bbc);
        urlSourceList.add(usaToday);

        member.setRecentLogin(LocalDateTime.now());
        member.setRoleType(RoleType.user);
        member.setRefreshTokenVersion(0L);
        member.setUrlSources(urlSourceList);
        memberCustomRepository.save(member);
        return ApiConstants.SUCCESS;
    }

    private void userIdValidationCheck(RegisterDto registerDto) {
        Member byUserId = memberCustomRepository.findByUserId(registerDto.getUserId());
        if(byUserId != null){
            throw new UserAlreadyExistException(ErrorCode.USER_ALREADY_EXISTS, registerDto.getUserId());
        }
    }

    private void passwordValidationCheck(RegisterDto registerDto) {
        if(!registerDto.getPassword().equals(registerDto.getPasswordConfirm())){
            throw new PasswordException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        if(registerDto.getPassword().length() > 20 || registerDto.getPassword().length()<10){
            throw new PasswordException(ErrorCode.PASSWORD_LENGTH_ERROR);
        }

        if(registerDto.getPassword().contains(registerDto.getUserId())
                || registerDto.getPassword().contains(registerDto.getEmail()))
        {
            throw new PasswordException(ErrorCode.PASSWORD_NOT_SECURE);
        }
    }


    @Data
    static class RegisterDto {
        public RegisterDto(@NonNull String userId, @NonNull String email, @NonNull String password, @NonNull String passwordConfirm) {
            this.userId = userId;
            this.email = email;
            this.password = password;
            this.passwordConfirm = passwordConfirm;
        }

        @NonNull
        String userId;
        @NonNull
        String email;
        @NonNull
        String password;
        @NonNull
        String passwordConfirm;
    }
}
