# kob后端

## 一、介绍

项目介绍：蛇蛇大作战线上对战平台

增加1：使用alpha-beta剪枝算法实现一个强ai

增加2：对战大厅实现公共聊天系统

增加3：对战回访实现弹幕

增加4：排行榜和对局列表使用redis

技术栈：JDK17 + SpringBoot3 + SpringCloud + SpringSecurity + JWT + WebSocket + Mysql + MyBatis-Plus + Docker

| 接口                      | 说明    |
|-------------------------|-------|
| /api/user/account/register | 注册    |
| /api/user/account/token | 登录    |
| /api/user/account/info  | 用户信息  |
| /api/user/bot/add       | 添加bot |
| /api/user/bot/getlist  | bot列表 |
| /api/user/bot/remove   | 移除bot |
| /api/user/bot/update  | 更新bot |
| /api/record/getlist   | 比赛记录  |
| /api/ranklist/getlist  | 排行榜   |
| /api/pk/start/game  | 开始游戏  |
| /api/pk/receive/bot/move   | bot移动 | 
