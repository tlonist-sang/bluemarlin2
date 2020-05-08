package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.annotation.LoginUser;
import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.domain.Keyword;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.Response;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.repository.MemberCustomRepository;
import com.project.bluemarlin2.bluemarlin2.repository.UrlSourceRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value="/api/v1/url")
@RequiredArgsConstructor
public class UrlSourceController {
    private final MemberCustomRepository memberCustomRepository;
    private final UrlSourceRepository urlSourceRepository;


    @GetMapping
    public Response getUrlList(@LoginUser Member member){
        List<UrlSource> urlSourceList = urlSourceRepository.findAllByUserId(member.getUserId());
        List<UrlSourceDto> data = urlSourceList.stream()
                .map(u -> new UrlSourceDto(u))
                .collect(toList());

        Response response = new Response<UrlSourceDto>();
        response.setData(data);
        response.setCount(data.size());
        response.setStatus(ApiConstants.SUCCESS);

        return response;
    }


    @Data
    static class UrlSourceDto {
        private String url;
        private List<KeywordDto> keywordDtoList;

        public UrlSourceDto(UrlSource u) {
            url = u.getUrl();
            keywordDtoList = u.getKeywords().stream()
                    .map(keyword -> new KeywordDto(keyword))
                    .collect(toList());
        }
    }

    @Data
    static class KeywordDto {
        String word;
        public KeywordDto(Keyword keyword) {
            word = keyword.getWord();
        }
    }

}
