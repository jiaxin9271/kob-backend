# kob后端

**技术栈**：SpringBoot、SpringCloud、SpringSecurity、Jwt、WebSocket、MybatisPlus、Redis、RabbitMQ

**项目描述**：类贪吃蛇的线上对战游戏，包含玩家匹配系统、联机对战、人机对战、个性定制Bot、大厅聊天室、对局录像回放、积分排行榜等功能。

- 使用 SpringSecurity、Jwt、Redis 进行身份验证，实现密码登录和第三方登录，使用 Pinia 保存用户信息实现**浏览器免登录**。使用**布隆过滤器**，防止登录接口滥用。Redis 中的用户信息使用**延迟双删**保证缓存一致性。
- 使用 TypeScript、Canvas、requestAnimationFrame 制作简易的**游戏引擎**，实现前端游戏界面的动态渲染。游戏地图的生成放在服务端实现。
- 将服务拆分成 4 个子系统：对战系统、匹配系统、Bot代码执行系统、消费者系统。将大厅聊天记录、对局记录、个人积分更新发送到 RabbitMQ 实现消息**异步解耦**，提升系统的稳定性。
- 内置 **ChatGPT** 机器人，实现与玩家聊天互动；基于 Prompt 工程**检测恶意言论**，在线禁言用户。涉及 OpenAI API 调用的功能全部放在消费者系统中执行，缓解对战系统的压力。
- 使用 Redis 的 **SortSet 结构**缓存对局列表和积分排行榜，对局列表以时间排序，排行榜以积分排序，这里使用异步更新保证缓存一致性。有玩家登顶排行榜可以大厅播报。
- 支持强弱人机对战，其中强人机使用 **Alpha-beta 剪枝算法**；也支持 Bot 对战，Bot 代码由玩家使用 Java 语言编写。

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
