package com.example.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class WsApplication {

    @GetMapping("/w")
    public String getWs() {
        return "ws";
    }

    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }

}
