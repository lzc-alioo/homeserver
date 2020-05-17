package com.alioo.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
public class MainController {

    public MainController() {
        System.out.println("MainController...");
    }

    @RequestMapping("/index")
    public String index(){
        System.out.println("main/index...");


        return "/main/index";
    }

}
