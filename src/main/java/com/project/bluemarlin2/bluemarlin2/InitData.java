package com.project.bluemarlin2.bluemarlin2;

import com.project.bluemarlin2.bluemarlin2.domain.Keyword;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.RoleType;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData{
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.initData();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final MemberService memberService;

        public void initData(){
            Member member = new Member()
                    .setEmail("tlonist.sang@gmail.com")
                    .setUserId("admin")
                    .setRoleType(RoleType.admin)
                    .setRefreshTokenVersion(1L)
                    .setPassword("admin");
            List<UrlSource> urlSourceList = new ArrayList();
            UrlSource fox = new UrlSource().setUrl("www.foxnews.com").setMember(member);
            UrlSource bbc = new UrlSource().setUrl("www.bbc.com").setMember(member);
            UrlSource usaToday = new UrlSource().setUrl("www.usatoday.com").setMember(member);

            List<Keyword> keywords1 = new ArrayList<>();
            Keyword keyword1 = new Keyword().setWord("Donald Trump").setUrlSource(fox);
            Keyword keyword2 = new Keyword().setWord("North Korea").setUrlSource(fox);
            keywords1.add(keyword1);
            keywords1.add(keyword2);
            fox.setKeywords(keywords1);

            List<Keyword> keywords2 = new ArrayList<>();
            Keyword keyword3 = new Keyword().setWord("Corona Virus").setUrlSource(bbc);
            keywords2.add(keyword3);
            bbc.setKeywords(keywords2);

            List<Keyword> keywords3 = new ArrayList<>();
            Keyword keyword4 = new Keyword().setWord("Tesla").setUrlSource(usaToday);
            Keyword keyword5 = new Keyword().setWord("Microsoft").setUrlSource(usaToday);
            keywords3.add(keyword4);
            keywords3.add(keyword5);
            usaToday.setKeywords(keywords3);

            urlSourceList.add(fox);
            urlSourceList.add(bbc);
            urlSourceList.add(usaToday);

            member.setUrlSources(urlSourceList);
            memberService.createNew(member);
        }
    }
}
