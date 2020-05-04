package com.project.bluemarlin2.bluemarlin2.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/register")
public class RegisterController {
    @PostMapping
    public String registerUser(){
        System.out.println("hello world!");
        return "hello";
    }
}
