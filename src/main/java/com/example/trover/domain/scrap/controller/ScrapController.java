package com.example.trover.domain.scrap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScrapController {

    @GetMapping("/testdd")
    @ResponseBody
    public String test() {

        return "test";
    }

}
