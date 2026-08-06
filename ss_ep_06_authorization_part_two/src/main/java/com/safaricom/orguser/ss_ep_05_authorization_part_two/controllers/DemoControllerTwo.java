package com.safaricom.orguser.ss_ep_05_authorization_part_two.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class DemoControllerTwo {

    @PostMapping("/test1")
    public String demo() {
        return "Demo";
    }

    @PostMapping("/test2")
    public String hello() {
        return "hello";
    }


}
