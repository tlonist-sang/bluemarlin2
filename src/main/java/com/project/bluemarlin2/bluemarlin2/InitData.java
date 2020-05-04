package com.project.bluemarlin2.bluemarlin2;

import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.RoleType;
import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import com.project.bluemarlin2.bluemarlin2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements ApplicationRunner {
    @Autowired
    private MemberService memberService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initData();
    }

    private void initData(){
        Member member = new Member()
                .setEmail("tlonist.sang@gmail.com")
                .setUserId("admin")
                .setRoleType(RoleType.admin)
                .setPassword("admin");
        List<UrlSource> urlSourceList = new ArrayList();
        UrlSource fox = new UrlSource().setAddress("www.foxnews.com").setMember(member);
        UrlSource bbc = new UrlSource().setAddress("www.bbc.com").setMember(member);
        UrlSource usaToday = new UrlSource().setAddress("www.usatoday.com").setMember(member);

        urlSourceList.add(fox);
        urlSourceList.add(bbc);
        urlSourceList.add(usaToday);

        member.setUrlSources(urlSourceList);
        memberService.createNew(member);
    }
}
