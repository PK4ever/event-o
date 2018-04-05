package com.example.evento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @RequestMapping("/controller")
    @ResponseBody
    public String helloWorld() {
        return " This is a new controller ";
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return " the begining of event-o ";
    }

}
