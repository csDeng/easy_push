package com.example.short_polling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@SpringBootApplication
@RestController
@EnableWebMvc
public class SSEApplication {


    private SseEmitter sseEmitter;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        sseEmitter = new SseEmitter();
        return sseEmitter;
    }

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody String message) {
        try {
            if (sseEmitter != null) {
                sseEmitter.send(SseEmitter.event().data(message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(SSEApplication.class, args);
    }

}
