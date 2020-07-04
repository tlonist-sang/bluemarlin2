package com.project.bluemarlin2.bluemarlin2.config;

import com.google.gson.Gson;
import com.project.bluemarlin2.bluemarlin2.domain.RoleType;
import com.project.bluemarlin2.bluemarlin2.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        return new JwtAuthenticationFilter();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .formLogin()
                .loginPage("/index.html")
                .loginProcessingUrl("/auth**")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .and()
                .authorizeRequests()

                .mvcMatchers(HttpMethod.GET, "/name").permitAll()
                .mvcMatchers(HttpMethod.POST, "/register").permitAll()
                .mvcMatchers(HttpMethod.POST, "/login-validation").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api").authenticated()
                .mvcMatchers(HttpMethod.GET, "/api").authenticated()
                .mvcMatchers(HttpMethod.DELETE, "/api").authenticated()
                .mvcMatchers(HttpMethod.PUT, "/api").authenticated()
                .mvcMatchers("/h2-console/**").permitAll()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
        http.cors();
        http.httpBasic().disable();

    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    private AuthenticationSuccessHandler successHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                Gson gson = new Gson();

                String accessToken = jwtTokenProvider.createAccessToken(authentication);
                String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("status", "success");
                resultMap.put("access_token", accessToken);
                resultMap.put("refresh_token", refreshToken);

                httpServletResponse.getWriter().append(gson.toJson(resultMap));
                httpServletResponse.setStatus(200);
            }
        };
    }

    private AuthenticationFailureHandler failureHandler(){
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.getWriter().append("Authentication failure");
                httpServletResponse.setStatus(401);
            }
        };
    }
}
