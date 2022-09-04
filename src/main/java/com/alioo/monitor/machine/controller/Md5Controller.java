package com.alioo.monitor.machine.controller;

import com.alioo.monitor.md5.Md5Main;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("md5")
public class Md5Controller {

    @Autowired
    private Md5Main md5Main;

    public Md5Controller() {
        log.info("MainController init...");
    }

    @ResponseBody
    @RequestMapping("/setMd5sleep")
    public Object setMd5sleep(long md5sleep) {

        Map<String, Long> map = new HashMap<>();
        map.put("before", md5Main.md5sleep);
        map.put("after", md5sleep);

        log.info("md5/setMd5sleep... map:{}", map);

        md5Main.md5sleep = md5sleep;

        return map;
    }

    @ResponseBody
    @RequestMapping("/setMd5count")
    public Object setMd5count(long md5count) {

        Map<String, Long> map = new HashMap<>();
        map.put("before", md5Main.md5count);
        map.put("after", md5count);

        log.info("md5/setMd5count... map:{}", map);

        md5Main.md5count = md5count;

        return map;
    }
}
