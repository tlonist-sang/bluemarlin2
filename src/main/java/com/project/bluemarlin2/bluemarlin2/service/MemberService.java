package com.project.bluemarlin2.bluemarlin2.service;

import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.MemberAccount;
import com.project.bluemarlin2.bluemarlin2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Member> byEmail = memberRepository.findByUserId(userName);
        if(!byEmail.isPresent()){

        }
        Member member = byEmail.get();
        return new MemberAccount(member);
    }

    public Member createNew(Member member){
        member.encodePassword(passwordEncoder);
        return this.memberRepository.save(member);
    }
}
