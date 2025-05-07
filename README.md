# online-judge-platform
一个基于 Vue 3 + Spring Boot + Spring Cloud 微服务 + Docker 的 编程题目在线评测系统 （简称 OJ）

## 一、项目功能

1. 题目模块
   1. 管理员：创建、删除、修改题目
   2. 用户：搜索题目、在线做题
2. 用户模块
   1. 注册
   2. 登录
3. 判题模块
   1. 提交判题（结果正确性）
   2. 错误处理（内存溢出、安全性、超时）
   3. 自主实现代码沙箱

## 二、技术栈

前端：Vue3、Arco Design组件库、axios、openapi、md编辑器、code编辑器

后端：SpringBoot、Java执行系统命令、Java安全管理器、docker、mysql、mybatis、mybatis-plus

核心设计：

![项目结构](/doc/images/项目结构.png)

时序图：

![项目时序图](/doc/images/项目时序图.png)

## 三、项目截图

### 用户登录

![用户登录](/doc/images/用户登录.png)

### 浏览题目

![浏览题目](/doc/images/浏览题目.png)

### 浏览题目提交

![浏览题目提交](/doc/images/浏览题目提交.png)

### 创建题目

![创建题目](/doc/images/创建题目.png)

### 管理题目

![管理题目](/doc/images/管理题目.png)



