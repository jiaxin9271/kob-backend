package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.InfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user/account")
@Tag(name = "用户信息")
public class InfoController {
    @Autowired
    private InfoService infoService;

    @GetMapping("/info")
    public Map<String, String> getinfo() {
        return infoService.getInfo();
    }
}
