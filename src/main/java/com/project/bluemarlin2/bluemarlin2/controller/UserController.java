package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.annotation.LoginUser;
import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.domain.CustomResponse;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.MemberDto;
import com.project.bluemarlin2.bluemarlin2.domain.RoleType;
import com.project.bluemarlin2.bluemarlin2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;

    @GetMapping(value="/all")
    public CustomResponse getAllUsers(@LoginUser Member member){
        CustomResponse customResponse = new CustomResponse();
        if(member.getRoleType() == RoleType.admin || member.getUserId().equals("admin")){
            List<MemberDto> allUserInfo = memberService.getAllUserInfo();
            customResponse.setStatus(ApiConstants.SUCCESS);
            customResponse.setData(allUserInfo);
            customResponse.setCount(allUserInfo.size());
            return customResponse;
        }
        return new CustomResponse(ApiConstants.FAIL);
    }
}
