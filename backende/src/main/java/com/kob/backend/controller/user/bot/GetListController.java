package com.kob.backend.controller.user.bot;

import com.kob.backend.pojo.Bot;
import com.kob.backend.service.user.bot.GetListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/bot")
@Tag(name = "Bot列表")
public class GetListController {
    @Autowired
    private GetListService getListService;

    @GetMapping("/getlist")
    public List<Bot> getList() {
        return getListService.getList();
    }
}
