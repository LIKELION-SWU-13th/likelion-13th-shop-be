package com.likelion13th.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/login")
    public String adminLoginForm() {
        return "admin/adminLoginForm";
    }

    @GetMapping("/admin/main")
    public String adminMain() {
        return "admin/adminMain";
    }

    @GetMapping("/admin/page")
    public String adminForm() {
        return "admin/adminForm";
    }
}


