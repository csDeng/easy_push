package com.example.short_polling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@RestController
@EnableWebMvc
public class PollingApplication {

    private String latestMessage;

    /**
     * 短轮询
     * @return
     */
    @GetMapping("/short")
    public String  getShort() {
        long l = System.currentTimeMillis();
        if((l&1) == 1) {
            return "ok";
        }
        return "fail";
    }


    /**
     * 长轮询
     * @return
     */
    @GetMapping("/long")
    public DeferredResult<String> getLong() {
        DeferredResult<String> deferredResult = new DeferredResult<>();

        if (latestMessage != null) {
            deferredResult.setResult(latestMessage);
        } else {
            // 使用定时任务设置超时时间
            TimerTask timeoutTask = new TimerTask() {
                @Override
                public void run() {
                    deferredResult.setResult(null);
                }
            };
            Timer timer = new Timer();
            timer.schedule(timeoutTask, 5000); // 设置超时时间为5秒

            // 设置回调函数，在消息到达时触发
            deferredResult.onTimeout(() -> {
                timer.cancel();
                deferredResult.setResult(null);
            });

            deferredResult.onCompletion(timer::cancel);
        }

        return deferredResult;
    }

    /**
     * 设置消息
     * @param message
     */
    @PostMapping("/send-message")
    public void sendMessage(@RequestBody String message) {
        latestMessage = message;
    }



    public static void main(String[] args) {
        SpringApplication.run(PollingApplication.class, args);
    }

}
