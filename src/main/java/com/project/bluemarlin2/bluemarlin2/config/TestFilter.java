package com.project.bluemarlin2.bluemarlin2.config;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("==========Test Filter entered==========");
        filterChain.doFilter(request, response);
        System.out.println("==========Test Filter entered==========");
    }
}
