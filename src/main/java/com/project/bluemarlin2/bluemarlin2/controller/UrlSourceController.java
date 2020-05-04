package com.project.bluemarlin2.bluemarlin2.controller;

import com.google.gson.Gson;
import com.project.bluemarlin2.bluemarlin2.domain.MemberAccount;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSourceDTO;
import com.project.bluemarlin2.bluemarlin2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/api/v1/url")
@RequiredArgsConstructor
public class UrlSourceController {
    private final MemberService memberService;


    @GetMapping
    public String getUrlList(Principal principal){
        Gson gson = new Gson();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal1 = authentication.getPrincipal();



        List<UrlSourceDTO> urlSourceDTOS = new ArrayList<>();
        MemberAccount memberAccount = (MemberAccount) memberService.loadUserByUsername(principal.getName());
        List<UrlSource> urlSources = memberAccount.getMember().getUrlSources();
        for (UrlSource urlSource : urlSources) {
            urlSourceDTOS.add(new UrlSourceDTO(urlSource.getId(), urlSource.getAddress()));
        }
        return gson.toJson(urlSourceDTOS);
    }
}
