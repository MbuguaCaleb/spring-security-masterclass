package com.safaricom.orguser.ss_ep_05_authorization_part_two.controllers;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    @GetMapping("/test1")
    public String demo() {
        return "Demo";
    }

    @PostMapping("/test2")
    public String hello() {
        return "hello";
    }

    @GetMapping("/test/{id}")
    public String test(@PathVariable String id) {
        return id;
    }

}
