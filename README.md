# 简介
一个基于 SSM 的 Java Web 项目快速启动器。  

# 目前的想法
- 整体设计
    1. 暂时不使用 Spring Boot，而是选择单独的框架整合。后续会推出 Spring Boot 的版本。  
    2. 前端后分离，该项目提供后端服务，前端分离出一个项目，使用 Vue.js。  
    3. 整体架构风格为 RESTful。  
    4. 权限框架暂时指定为 Shiro，该框架对于 RESTful 的支持可能不太好，需要改造。  
    5. SSM 框架整合尽量减少使用 XML，更多的使用 Java Config 和 注解。  
    6. 对于 Session 的操作要分离出来一个工具类，方便后续使用 JWT。  

- UI 选择
    1. 前端框架使用 Vue.js，在这之上，尽量少的编写 Vue 组件，更多的使用现有的组件。  
    2. 目前的 UI admin 有三种选择，一个是 iview 团队的 iview-admin，一个是基于 element ui 的 vue-element-admin，还有一个是基于 ant-design 的 ant-design-vue。  

