package com.meet.Frontend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meet")
@RequiredArgsConstructor
public class FrontendController {
    @GetMapping("")
    public String home() {
        return "meet/frontend/index";
    }
}
