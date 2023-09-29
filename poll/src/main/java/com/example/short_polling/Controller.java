package com.example.short_polling;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/s")
    public String s() {
        return "s";
    }

    @GetMapping("/l")
    public String l() {
        return "l";
    }
}
