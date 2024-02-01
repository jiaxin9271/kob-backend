package com.kob.backend.consumer;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.utils.JwtUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring管理的都是单例（singleton）和 websocket （多对象）相冲突。每一个客户端请求服务端都会生成一个新的websocket实例，
 * spring 仅仅针对@component、@controller注解完成单例模式管理任务（注意仅仅这一次、这一个对象赋值了）
 * 后续随着客户单端访问 websocket不断进行实例化（这些对象不是由spring管理的，
 * 所以启动过程并无法给后续的实际处理websocket会话的实例赋值）
 * <p>
 * xxMapper、xxUrl等需要注入的东西，都需要设置成static，使用构造器的方式注入
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{token}")
public class WebSocketServer {
    final public static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>(); // 全局线程安全的哈希表
    private User user;
    private Session session = null;
    public static UserMapper userMapper;
    public static RecordMapper recordMapper;
    private static BotMapper botMapper;
    public static RestTemplate restTemplate;
    public Game game = null;
    private static String addPlayerUrl;
    private static String removePlayerUrl;
    public static String addBotUrl;

    @Value("${matching-system.addPlayerUrl}")
    public void setAddPlayerUrl(String addPlayerUrl) {
        WebSocketServer.addPlayerUrl = addPlayerUrl;
    }

    @Value("${matching-system.removePlayerUrl}")
    public void setRemovePlayerUrl(String removePlayerUrl) {
        WebSocketServer.removePlayerUrl = removePlayerUrl;
    }

    @Value("${botrunning-system.addBotUrl}")
    public void setAddBotUrl(String addBotUrl) {
        WebSocketServer.addBotUrl = addBotUrl;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setRecordMapper(RecordMapper recordMapper) {
        WebSocketServer.recordMapper = recordMapper;
    }

    @Autowired
    public void setBotMapper(BotMapper botMapper) {
        WebSocketServer.botMapper = botMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        WebSocketServer.restTemplate = restTemplate;
    }

    /**
     * 建立连接
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        Integer userId = JwtUtil.parseJWTAndGetSubject(token);
        this.user = userMapper.selectById(userId);
        if (this.user != null) {
            users.put(userId, this);
        } else {
            this.session.close();
        }
        log.info("connected!");
        log.info("users: " + users);
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() {
        log.info("disconnected!");
        if (this.user != null) {
            users.remove(this.user.getId());
        }
    }

    /**
     * 接受客户端的信息
     */
    @OnMessage
    public void onMessage(String message, Session session) {  // 当做路由
        log.info("receive message!");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if ("start-matching".equals(event)) {
            startMatching(data.getInteger("bot_id"));
        } else if ("stop-matching".equals(event)) {
            stopMatching();
        } else if ("move".equals(event)) {
            move(data.getInteger("direction"));
        }
    }

    /**
     * 从服务器向客服端发送信息
     */
    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("exception message", e);
            }
        }
    }

    public static void startGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId) {
        User a = userMapper.selectById(aId);
        User b = userMapper.selectById(bId);
        Bot botA = botMapper.selectById(aBotId);
        Bot botB = botMapper.selectById(bBotId);

        Game game = new Game(13, 14, 20, a.getId(), botA, b.getId(), botB);
        if (users.get(a.getId()) != null) {
            users.get(a.getId()).game = game;
        }
        if (users.get(b.getId()) != null) {
            users.get(b.getId()).game = game;
        }

        game.start(); // 开启多线程
        JSONObject respGame = new JSONObject();
        respGame.put("a_id", game.getPlayerA().getId());
        respGame.put("a_sx", game.getPlayerA().getSx());
        respGame.put("a_sy", game.getPlayerA().getSy());
        respGame.put("b_id", game.getPlayerB().getId());
        respGame.put("b_sx", game.getPlayerB().getSx());
        respGame.put("b_sy", game.getPlayerB().getSy());
        respGame.put("map", game.getG());

        JSONObject respA = new JSONObject();
        respA.put("event", "start-matching");
        respA.put("opponent_username", b.getUsername());
        respA.put("opponent_photo", b.getPhoto());
        respA.put("game", respGame);
        if (users.get(a.getId()) != null) {
            users.get(a.getId()).sendMessage(respA.toJSONString());
        }

        JSONObject respB = new JSONObject();
        respB.put("event", "start-matching");
        respB.put("opponent_username", a.getUsername());
        respB.put("opponent_photo", a.getPhoto());
        respB.put("game", respGame);
        if (users.get(b.getId()) != null) {
            users.get(b.getId()).sendMessage(respB.toJSONString());
        }
    }

    private void startMatching(Integer botId) {
        log.info("start matching!");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("rating", this.user.getRating().toString());
        data.add("bot_id", botId.toString());
        restTemplate.postForObject(addPlayerUrl, data, String.class);
    }

    private void stopMatching() {
        log.info("stop matching");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        restTemplate.postForObject(removePlayerUrl, data, String.class);
    }

    private void move(int direction) {
        log.info("move " + direction);
        if (game.getPlayerA().getId().equals(user.getId())) {
            if (game.getPlayerA().getBotId().equals(-1)) { // 亲自出马
                game.setNextStepA(direction);
            }
        } else if (game.getPlayerB().getId().equals(user.getId())) { // 亲自出马
            if (game.getPlayerB().getBotId().equals(-1)) {
                game.setNextStepB(direction);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("exception message", error);
    }
}
