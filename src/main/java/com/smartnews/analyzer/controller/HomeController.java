package com.smartnews.analyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Smart News Analyzer에 오신 것을 환영합니다!");
        model.addAttribute("version", "1.0.0");
        model.addAttribute("developer", "주니어 개발자");
        return "home";
    }
    
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("testMessage", "테스트 페이지입니다!");
        return "test";
    }
    
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("greeting", "안녕하세요! Spring Boot가 정상 작동합니다.");
        return "hello";
    }
}