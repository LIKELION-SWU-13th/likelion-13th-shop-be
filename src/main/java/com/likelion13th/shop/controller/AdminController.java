package com.likelion13th.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
    @GetMapping("")
    public String admin(){
        return "/admin/admin";
    }
    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "/admin/accessDenied";
    }
}
