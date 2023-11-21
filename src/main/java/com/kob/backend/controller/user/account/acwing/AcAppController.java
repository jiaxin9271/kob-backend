package com.kob.backend.controller.user.account.acwing;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.service.user.account.acwing.AcAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user/account/acwing/acapp")
public class AcAppController {
    @Autowired
    private AcAppService acAppService;

    @GetMapping("/apply_code")
    public JSONObject applyCode() {
        return acAppService.applyCode();
    }

    @GetMapping("/receive_code")
    public JSONObject receiveCode(@RequestParam Map<String, String> data) {
        String code = data.get("code");
        String state = data.get("state");
        return acAppService.receiveCode(code, state);
    }
}
