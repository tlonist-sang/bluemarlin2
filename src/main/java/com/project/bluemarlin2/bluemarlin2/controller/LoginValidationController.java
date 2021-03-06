package com.project.bluemarlin2.bluemarlin2.controller;

import com.google.gson.Gson;
import com.project.bluemarlin2.bluemarlin2.domain.Member;
import com.project.bluemarlin2.bluemarlin2.domain.MemberAccount;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;



@RestController
@RequestMapping(value="/login-validation")
public class LoginValidationController {

    /**
     * @param principal
     * @return
     *
     * This method is required to give non-500 Error as a response of login-validation request.
     * Despite the presence of filters, if a requested url has no matching handler designated by the dispatcherservlet,
     * there will be an 500 internal error.
     */
    @GetMapping
    public String validateLoginUsingToken(Principal principal){
        Gson gson = new Gson();
        Member member = ((MemberAccount) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember();
        return member.getUserId();
    }
}