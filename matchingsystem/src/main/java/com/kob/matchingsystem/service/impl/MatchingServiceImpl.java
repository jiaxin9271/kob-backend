package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.MatchingService;
import com.kob.matchingsystem.service.impl.utils.MatchingPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchingServiceImpl implements MatchingService {
    // 全局只有一个匹配池，匹配池是一个线程，main是一个线程
    public final static MatchingPool matchingPool = new MatchingPool();


    @Override
    public String addPlayer(Integer userId, Integer rating, Integer botId) {
        matchingPool.addPlayer(userId, rating, botId);
        log.info("matchingPool: " + matchingPool.getPlayerNumber());
        return "add player success";
    }

    @Override
    public String removePlayer(Integer userId) {
        matchingPool.removePlayer(userId);
        log.info("matchingPool: " + matchingPool.getPlayerNumber());
        return "remove player success";
    }
}