package com.project.bluemarlin2.bluemarlin2.controller;

import com.google.gson.Gson;
import com.project.bluemarlin2.bluemarlin2.domain.Keyword;
import com.project.bluemarlin2.bluemarlin2.domain.keywordDtos.DeleteKeywordDto;
import com.project.bluemarlin2.bluemarlin2.domain.keywordDtos.KeywordDTO;
import com.project.bluemarlin2.bluemarlin2.domain.MemberAccount;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.service.KeywordService;
import com.project.bluemarlin2.bluemarlin2.service.MemberService;
import com.project.bluemarlin2.bluemarlin2.service.UrlSourceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/keyword")
@RequiredArgsConstructor
public class KeywordController {
    private final UrlSourceService urlSourceService;
    private final MemberService memberService;
    private final KeywordService keywordService;

    @GetMapping
    public String getKeywords(Principal principal){
        MemberAccount memberAccount = (MemberAccount)memberService.loadUserByUsername(principal.getName());
        List<UrlSource> urlSources = memberAccount.getMember().getUrlSources();

        HashMap<String, List<KeywordDTO>> map = new HashMap<>();
        for (UrlSource urlSource : urlSources) {
            map.put(urlSource.getUrl(), urlSource.getWords());
        }

        Gson gson = new Gson();
        return gson.toJson(map);
    }

    @PostMapping
    public void createKeyword(@RequestBody String newWord, @RequestBody Long urlSourceId){
        UrlSource urlSource = urlSourceService.findOne(urlSourceId);

        Keyword keyword = new Keyword();
        keyword.setWord(newWord);
        keyword.setUrlSource(urlSource);

        urlSource.getKeywords().add(keyword);
        keywordService.save(keyword);
    }

    @DeleteMapping
    public void deleteKeyword(@RequestBody DeleteKeywordDto deleteKeywordDto )
    {
        keywordService.remove(deleteKeywordDto);
    }
}
