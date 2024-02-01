package com.kob.botrunningsystem.service.impl.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这里本质上是一个消息队列：
 * 1. addBot拿到锁以后就不断地往里面添加bot，没有锁就堵住
 * 2. run的时候拿到锁，如果有bot，就取出来执行；没有bot就解锁等待
 */
@Slf4j
public class BotPool extends Thread {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Queue<Bot> bots = new LinkedList<>();

    public int getBotNumber() {
        lock.lock();
        try {
            return bots.size();
        } finally {
            lock.unlock();
        }
    }

    public void addBot(Integer userId, String botCode, String input) {
        lock.lock();
        try {
            bots.add(new Bot(userId, botCode, input));
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 为了简便，这里只能执行java代码，如果想要安全地扩展，可以考虑docker。
     * 为了让执行时间可控，这里每一个consumer单独开一个线程。
     */
    private void consume(Bot bot) {
        Consumer consumer = new Consumer();
        consumer.startTimeout(2000, bot);
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (bots.isEmpty()) {
                try {
                    condition.await();  // 等待bot输入，默认有一个解锁的操作
                } catch (InterruptedException e) {
                    log.error("error message", e);
                    lock.unlock();
                    break;
                }
            } else {
                Bot bot = bots.remove();
                lock.unlock();
                consume(bot);  // 比较耗时，可能会执行几秒钟
            }
        }
    }
}
