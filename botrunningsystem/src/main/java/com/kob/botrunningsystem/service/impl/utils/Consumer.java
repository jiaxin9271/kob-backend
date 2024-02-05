package com.kob.botrunningsystem.service.impl.utils;

import lombok.extern.slf4j.Slf4j;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@Component
public class Consumer extends Thread {
    private Bot bot;
    private static RestTemplate restTemplate;
    private static String receiveBotMoveUrl;

    @Value("${backend.receiveBotMoveUrl}")
    public void setReceiveBotMoveUrl(String receiveBotMoveUrl) {
        Consumer.receiveBotMoveUrl = receiveBotMoveUrl;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        Consumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeout, Bot bot) {
        this.bot = bot;
        this.start();

        try {
            // sleep不好，因为有的代码会提前结束，但是sleep会强制休息时间
            this.join(timeout);  // 最多等待timeout秒
        } catch (InterruptedException e) {
            log.error("exception message", e);
        } finally {
            this.interrupt();  // 终结当前线程
        }
    }

    private String addUid(String code, String uid) {  // 在code中的Bot类名后添加uid
        int k = code.indexOf(" implements java.util.function.Supplier<Integer>");
        return code.substring(0, k) + uid + code.substring(k);
    }

    @Override
    public void run() {
        UUID uuid = UUID.randomUUID(); // 每次返回一个不同的id
        String uid = uuid.toString().substring(0, 8);
        Supplier<Integer> botInterface = Reflect.compile(
                "com.kob.botrunningsystem.utils.Bot" + uid,
                addUid(bot.getBotCode(), uid)
        ).create().get();

        File file = new File("botrunningsystem/input.txt");
        try (PrintWriter fout = new PrintWriter(file)) {
            fout.println(bot.getInput());
            fout.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Integer direction = botInterface.get();
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", bot.getUserId().toString());
        data.add("direction", direction.toString());
        restTemplate.postForObject(receiveBotMoveUrl, data, String.class);
    }
}
