package com.project.bluemarlin2.bluemarlin2.util;

import com.project.bluemarlin2.bluemarlin2.constants.SecurityConstant;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.MemberAccount;
import com.project.bluemarlin2.bluemarlin2.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value(value="${secret.key}")
    private String secretKey;

    @Autowired
    private MemberService memberService;

    public String resolveAccessToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }


    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("X-REFRESH-TOKEN");
    }

    public String createRefreshToken(Authentication authentication){
        Map<String, Object> claims = new HashMap<>();
        String username = ((MemberAccount) authentication.getPrincipal()).getUsername();
        String password = ((MemberAccount) authentication.getPrincipal()).getPassword();

        Member member = memberService.increaseRefreshTokenVersion(username);

        claims.put("version", member.getRefreshTokenVersion());
        claims.put("username", username);
        claims.put("password", password);
        return createToken(claims, username, SecurityConstant.REFRESH_TOKEN_EXPIRE_TIME);
    }

    public String createAccessToken(Authentication authentication){
        Map<String, Object> claims = new HashMap<>();
        String username = ((MemberAccount) authentication.getPrincipal()).getUsername();
        String password = ((MemberAccount) authentication.getPrincipal()).getPassword();
        claims.put("username", username);
        claims.put("password", password);
        return createToken(claims, username, SecurityConstant.ACCESS_TOKEN_EXPIRE_TIME);
    }

    private String createToken(Map<String, Object> claims, String subject, Long duration){
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + duration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            String subject = claimsJws.getBody().getSubject();
            MemberAccount memberAccount = (MemberAccount) memberService.loadUserByUsername(subject);
            Integer version = (Integer)claimsJws.getBody().get("version");

            if(version.longValue() !=
                    memberAccount.getMember().getRefreshTokenVersion().longValue())
                return false;

            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
