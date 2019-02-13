<p align="center">
     <img src="https://github.com/nekolr/ssm-starter/blob/master/snapshot/ssm-starter.png">
     <br/>
     <img src="https://img.shields.io/badge/JDK-8-blue.svg?style=flat-square">
     <img src="https://img.shields.io/github/stars/nekolr/ssm-starter.svg?style=flat-square&label=Stars">
     <img src="https://img.shields.io/github/forks/nekolr/ssm-starter.svg?style=flat-square&label=Fork">
     <img src="https://img.shields.io/github/license/mashape/apistatus.svg?style=flat-square">
</p>

# 简介
一个基于 SSM + Shiro + Dubbo 的 RESTful Web 应用快速启动器。  

# 模块结构
```
ssm-starter
     ├─ssm-starter-common                       公共模块
     ├─ssm-starter-admin                             后台管理系统
     |        ├─ssm-starter-admin-common             公共模块
     |        ├─ssm-starter-admin-api                API 模块
     |        ├─ssm-starter-admin-provider           服务提供模块
     |        ├─ssm-starter-admin-server             Web 服务模块
```

# 依赖关系
将项目从生产者和消费者角度分成 `ssm-starter-admin-provider` 和 `ssm-starter-admin-server`，其中 provider 服务包含系统所有的业务，而 server 服务只负责消费 provider 提供的服务。  

启动 provider 服务，其中一种方式就是自定义一个启动类，在 main 方法里使用 `ClassPathXmlApplicationContext` 来指定 `dubbo-provider.xml` 启动。这里使用的是 Dubbo 官方提供的一个启动类 `com.alibaba.dubbo.container.Main` 来启动服务，能够轻松实现优雅停机。  

# 编译及打包
`mvn clean package`  

# 运行
由于使用了 `exec-maven-plugin` 插件，因此可以直接使用 Maven 启动 Dubbo 服务提供者。来到 `ssm-starter-admin-provider` 模块目录，使用以下命令启动服务：

```shell
mvn org.codehaus.mojo:exec-maven-plugin:java -f pom.xml
```

`ssm-starter-admin-server` 模块则通过 Servlet 容器启动。

# 技术选型
- 后端
  
| 技术 | 主要作用 | 说明 |
| ------------ | ------------ | ------------ |
| Spring Framework | bean 容器 | |
| Spring MVC | MVC 框架 | |
| MyBatis | ORM 框架 | |
| MyBatis Plus | MyBatis 增强工具 | 提供通用 CURD，以及代码生成等。因为项目需要，添加了自定义的代码生成模板 |
| Apache Shiro | 安全框架 | 由于 Shiro 对 RESTful 的支持不太好，所以对该框架进行了改造 |
| Redis | key-value 数据库 | |
| Druid | 数据库连接池 | |
| Dubbo | RPC 与服务治理 | |
| ZooKeeper | 分布式数据协调服务 | Dubbo 使用，作为注册中心 |
| Velocity | 模板引擎 | 只使用它提供代码生成功能 |
| JWT | 令牌协议 | |
| ESAPI | Web 解决方案 | 主要使用它实现 Xss 消息过滤 |
| cglib | Java 代码生成类库 | 主要使用它的 BeanCopier 实现对象复制 |
| Lombok | Java 代码插入 | 自动生成 Getter 和 Setter 等，需要 IDE 安装 Lombok 插件 |
| Fastjson | JSON 处理器 | 主要使用它构建 JSON 处理工具，MVC 框架中使用的还是 Jackson |
| sequence | 分布式 ID 生成器 | MyBatis Plus 默认集成，详情： <https://gitee.com/yu120/sequence> |
| Log4j 2 | 日志框架 | 日志门面使用 Slf4j |
| Hutool | Java 工具集 | 主要使用它进行加密解密，详情：<https://gitee.com/loolly/hutool> |
| hibernate validator | 请求参数校验 | 除了修改 Spring MVC 默认的请求参数校验，增加参数校验切面，省去编写校验代码 |
| Swagger2 | RESTful 接口文档自动生成 |  |
- 前端

# 说明
1. 暂时不使用 Spring Boot，而是选择单独的框架整合。后续会推出 Spring Boot 的版本。  
2. 前后端分离，该项目提供后端服务，前端分离出一个项目，使用 Vue.js。  
3. 整体架构风格为 RESTful。  
4. 权限框架指定为 Shiro，该框架对于 RESTful 的支持可能不太好，需要改造。  
5. SSM 框架整合使用 Java Config 和注解。  
6. 使用 JWT 验证身份，Shiro 鉴权。  
7. 使用 Dubbo 提供分布式服务支持。  
8. 使用 Redis 提供缓存支持。  
9. Entity、DAO、Service 和 Controller 代码生成功能。  
11. 目前的 UI admin 有三种选择，一个是 iview 团队的 iview-admin，一个是基于 element-ui 的 vue-element-admin，还有一个是基于 ant-design 的 ant-design-vue。  

# 进度
- [x] Spring + Spring MVC + MyBatis 整合  
- [x] Apache Shiro 改造和 JWT 集成  
- [x] 自定义代码生成模板  
- [x] 代码生成器  

# 可能用到的参考
- RESTful
    - [restful-api-design-references](https://github.com/aisuhua/restful-api-design-references)
    - [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
    - [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
    - [使用 Spring HATEOAS 开发 REST 服务](https://www.ibm.com/developerworks/cn/java/j-lo-SpringHATEOAS/index.html?ca=drs-&utm_source=tuicool&utm_medium=referral)
- JWT
    - [The Ins and Outs of Token Based Authentication](https://scotch.io/tutorials/the-ins-and-outs-of-token-based-authentication)
# 特别感谢
- [bootshiro](https://github.com/tomsun28/bootshiro)（本项目的 shiro 改造和 JWT 集成代码参考来源）
