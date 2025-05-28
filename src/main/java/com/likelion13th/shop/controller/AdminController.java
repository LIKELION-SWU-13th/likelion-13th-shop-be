package com.likelion13th.shop.controller;

import com.likelion13th.shop.dto.MemberFormDto;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String adminForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "admin/adminForm";
    }

    @PostMapping(value="/new")
    public String adminForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "admin/adminForm";
        }
        try{
            Member member = Member.createAdmin(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/adminForm";
        }

        return "redirect:/";
    }

    @GetMapping("/page")
    public String adminPage(Model model) {
        model.addAttribute("message", "관리자 전용 페이지입니다.");
        return "admin/adminMain";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model) {
        model.addAttribute("errorMessage", "접근 권한이 없습니다.");
        return "admin/accessDenied";
    }
}
