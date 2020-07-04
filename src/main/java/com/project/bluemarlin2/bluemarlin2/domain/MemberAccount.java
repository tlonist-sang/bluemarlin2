package com.project.bluemarlin2.bluemarlin2.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MemberAccount extends User {
    private Member member;

    public MemberAccount(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MemberAccount(Member member){

        super(member.getUserId(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_" + member.getRoleType())));//List.of(new SimpleGrantedAuthority("ROLE_" + member.getRoleType())));
        this.member = member;
    }

    public MemberAccount(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Member getMember() {
        return member;
    }

    public Map<String, Object> getClaims(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getUserId());
        claims.put("password", member.getPassword());
        return claims;
    }
}
