package com.alioo.monitor.machine.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("main")
public class MainController {

    public MainController() {
        log.info("MainController init...");
    }

    @RequestMapping("/index")
    public String index() {
        log.info("main/index...");


        return "/main/index";
    }

}
