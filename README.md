# 简介
一个基于 SSM 的 Java Web 项目快速启动器。  

# 目前的想法
- 整体设计
    1. 暂时不使用 Spring Boot，而是选择单独的框架整合。后续会推出 Spring Boot 的版本。  
    2. 前后端分离，该项目提供后端服务，前端分离出一个项目，使用 Vue.js。  
    3. 整体架构风格为 RESTful。  
    4. 权限框架暂时指定为 Shiro，该框架对于 RESTful 的支持可能不太好，需要改造。  
    5. SSM 框架整合尽量减少使用 XML，更多的使用 Java Config 和注解。  
    6. 无状态设计，使用 JWT 验证身份，Shiro 鉴权。  
    7. 使用 Dubbo 提供分布式服务支持。  
    8. 使用 Redis 提供缓存支持。  
    9. 代码生成功能。  

- UI 选择
    1. 前端框架使用 Vue.js，在这之上，尽量少的编写 Vue 组件，更多的使用现有的组件。  
    2. 目前的 UI admin 有三种选择，一个是 iview 团队的 iview-admin，一个是基于 element-ui 的 vue-element-admin，还有一个是基于 ant-design 的 ant-design-vue。  

# 结构
```
ssm-starter
     ├─ssm-starter-common                       公共模块
     ├─ssm-starter-upms                         用户权限管理系统
     |        ├─ssm-starter-upms-server             Web 服务模块
     |        ├─ssm-starter-upms-provider           服务提供模块
     |        ├─ssm-starter-upms-dao                持久层模块
     |        ├─ssm-starter-upms-common             公共模块
     |        ├─ssm-starter-upms-api                API 模块
```

# 可能用到的参考
- RESTful
    - [restful-api-design-references](https://github.com/aisuhua/restful-api-design-references)
    - [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
    - [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
    - [使用 Spring HATEOAS 开发 REST 服务](https://www.ibm.com/developerworks/cn/java/j-lo-SpringHATEOAS/index.html?ca=drs-&utm_source=tuicool&utm_medium=referral)