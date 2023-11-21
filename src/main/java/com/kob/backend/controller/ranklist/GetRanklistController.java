package com.kob.backend.controller.ranklist;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.service.ranklist.GetRanklistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ranklist")
@Tag(name = "排行榜")
public class GetRanklistController {
    @Autowired
    private GetRanklistService getRanklistService;

    @GetMapping("/getlist")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getRanklistService.getList(page);
    }
}
