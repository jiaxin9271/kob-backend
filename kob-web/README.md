# Vue 3 + TypeScript + Vite + Pinia

## 初始化项目

### 插件

[Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

### 创建项目

```shell
npm create vite@latest kob-web -- --template vue-ts  
```

### 安装依赖

```shell
# 路径别名
yarn add @types/node -D  

# eslint 安装
yarn add eslint -D

# eslint vue插件安装
yarn add eslint-plugin-vue -D

# eslint 识别ts语法
yarn add @typescript-eslint/parser -D

# eslint ts默认规则补充
yarn add @typescript-eslint/eslint-plugin -D

# eslint prettier插件安装
yarn add eslint-plugin-prettier -D

# 用来解决与eslint的冲突
yarn add eslint-config-prettier -D 

# 安装prettier
yarn add prettier -D

# 安装husky,一个为git客户端增加hook的工具
yarn add husky -D 

# 仅对Git 代码暂存区文件进行处理，配合husky使用
yarn add lint-staged -D

# 让commit信息规范化
yarn add @commitlint/cli @commitlint/config-conventional -D

# 路由
yarn add vue-router

# pinia 替代vuex
yarn add pinia

# scss
yarn add sass -D

# element-plus
yarn add element-plus @element-plus/icons-vue
yarn add unplugin-vue-components unplugin-auto-import -D

# axios
yarn add axios

# 打包，生成.gz文件
yarn add vite-plugin-compression -D
```

###  yarn指令
```shell
# 安装yarn
npm install -g yarn

# 安装依赖，类比 npm install
yarn

# 安装依赖
yarn add ...

# 运行
yarn run ...
```

## 项目架构

### ts配置

tsconfig.json、tsconfig.node.json、src/typings.d.ts

检查ts类型错误：yarn run ts

### 路径别名

vite.config.ts中通过baseUrl与paths属性修改路径映射

### ESLint 和 prettier

.eslintrc、.eslintignore、.prettierrc、.prettierignore

修复语法错误：yarn run lint

### Git

```shell
git commit -m <type>[optional scope]: <description> # 注意冒号后面有空格
- type：提交的类型（如新增、修改、更新等）
- optional scope：涉及的模块，可选
- description：任务描述

# 示范
git commit -m 'feat: 增加 xxx 功能'
```
| type     | description                             |
| -------- | ----------------------------------------|
| feat     | 新功能                                  |
| fix      | 修复 bug                                |
| style    | 样式修改（UI校验）                       |
| docs     | 文档更新                                |
| refactor | 重构代码(既没有新增功能，也没有修复 bug)  |
| perf     | 优化相关，比如提升性能、体验              |
| test     | 增加测试，包括单元测试、集成测试等        |
| build    | 构建系统或外部依赖项的更改               |
| ci       | 自动化流程配置或脚本修改                 |
| revert   | 回退某个commit提交                      |

### 环境变量

.env（所有环境生效）

.env.development（开发环境配置） 

.env.production（生产环境配置）

### 打包

```shell
yarn run build
```
