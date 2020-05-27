package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.domain.CustomResponse;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.repository.MemberRepository;
import com.project.bluemarlin2.bluemarlin2.repository.UrlSourceRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/v1/url-setting")
public class UrlSettingController {
    private final UrlSourceRepository urlSourceRepository;
    private final MemberRepository memberRepository;

    @GetMapping
    public CustomResponse<UrlSettingDto> getUrlSetting(Long urlId){
        Optional<UrlSource> byId = urlSourceRepository.findById(urlId);
        if(byId.isPresent()){
            UrlSource urlSource = byId.get();
            UrlSettingDto urlSettingDto = new UrlSettingDto(urlSource.getId(), urlSource.getMailingInterval(), urlSource.getKeywordIntersection());
            return new CustomResponse(ApiConstants.SUCCESS, urlSettingDto);
        }
        return new CustomResponse<>(ApiConstants.FAIL, null);
    }

    @PostMapping
    public CustomResponse updateUrlSetting(@RequestBody UrlSettingDto urlSettingDto){
        Optional<UrlSource> byId = urlSourceRepository.findById(urlSettingDto.getUrlId());
        if(byId.isPresent()){
            UrlSource urlSource = byId.get();
            urlSource.setKeywordIntersection(urlSettingDto.getKeywordIntersection());
            urlSource.setMailingInterval(urlSettingDto.getMailingInterval());
            urlSourceRepository.save(urlSource);
            return new CustomResponse(ApiConstants.SUCCESS);
        }
        return new CustomResponse<>(ApiConstants.FAIL);
    }

    @Data
    static class UrlSettingDto {
        public UrlSettingDto(Long urlId, Integer mailingInterval, Boolean keywordIntersection) {
            this.urlId = urlId;
            this.mailingInterval = mailingInterval;
            this.keywordIntersection = keywordIntersection;
        }

        Long urlId;
        Integer mailingInterval;
        Boolean keywordIntersection;
    }
}
