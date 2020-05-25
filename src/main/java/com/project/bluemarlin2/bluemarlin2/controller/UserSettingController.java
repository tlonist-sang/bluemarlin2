package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.annotation.LoginUser;
import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.domain.CustomResponse;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v1/setting")
@RequiredArgsConstructor
public class UserSettingController {
    private final MemberRepository memberRepository;


    @GetMapping
    public CustomResponse getUserSetting(@LoginUser Member member){
        CustomResponse<UserSettingDto> customResponse = new CustomResponse();
        if(member == null || member.getKeywordIntersection()==null || member.getMailIntervalInMinutes()<0){
            customResponse.setStatus(ApiConstants.FAIL);
        }else {
            UserSettingDto userSettingDto = new UserSettingDto(
                    member.getMailIntervalInMinutes(),
                    member.getKeywordIntersection());

            customResponse.setStatus(ApiConstants.SUCCESS);
            customResponse.setCount(1);
            customResponse.setDatum(userSettingDto);
        }
        return customResponse;
    }


    @PostMapping
    public CustomResponse updateUserSetting(@LoginUser Member member, @RequestBody UserSettingDto userSettingDto){
        CustomResponse customResponse = new CustomResponse();
        member.setKeywordIntersection(userSettingDto.getKeywordIntersection());
        member.setMailIntervalInMinutes(userSettingDto.getMailingInterval());
        Member save = memberRepository.save(member);

        if(save != null){
            customResponse.setStatus(ApiConstants.SUCCESS);
        }else{
            customResponse.setStatus(ApiConstants.FAIL);
        }
        return customResponse;
    }

    @Data
    static class UserSettingDto {
        public UserSettingDto(int mailingInterval, Boolean keywordIntersection) {
            this.mailingInterval = mailingInterval;
            this.keywordIntersection = keywordIntersection;
        }

        int mailingInterval;
        Boolean keywordIntersection;
    }
}
