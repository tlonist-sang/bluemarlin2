package com.project.bluemarlin2.bluemarlin2.config;

import com.project.bluemarlin2.bluemarlin2.service.MemberService;
import com.project.bluemarlin2.bluemarlin2.util.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ApplicationProperties applicationProperties;


    public Authentication getAuthentcation(String token){
        UserDetails userDetails = memberService.loadUserByUsername(getUserNameFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(applicationProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().contains("/api")) {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if (token != null && jwtTokenProvider.validateToken(token)) {

                Authentication authentcation = getAuthentcation(token);
                SecurityContextHolder.getContext().setAuthentication(authentcation);
                filterChain.doFilter(request, response);
                return;
            }else{
                throw new ServletException("Not authenticated!");
            }
        } else if ("/login-validation".equals(request.getRequestURI())){
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
//                Authentication authentcation = getAuthentcation(token);
//                SecurityContextHolder.getContext().setAuthentication(authentcation);
//                request.setAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, ((MemberAccount)(authentcation.getPrincipal())).getMember().getUserId());
//                request.setAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, ((MemberAccount)(authentcation.getPrincipal())).getMember().getUserId());

                Authentication authentcation = getAuthentcation(token);
                SecurityContextHolder.getContext().setAuthentication(authentcation);
                filterChain.doFilter(request, response);

//                Gson gson = new Gson();
//                Map<String, String> resultMap = new HashMap<>();
//                resultMap.put("status", "success");
//                resultMap.put("token", token);
//
//                response.getWriter().append(gson.toJson(resultMap));
//                response.setStatus(200);
//
//                filterChain.doFilter(request, response);
                return;
            }else{
                throw new ServletException("Not authenticated!");
            }
        }
        filterChain.doFilter(request, response);
    }


}
