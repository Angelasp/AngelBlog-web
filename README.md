AngelBlog 一个简洁美观、功能强大并且自适应的Java博客。使用springboot开发，前端使用Bootstrap。支持移动端自适应，配有完备的前台和后台管理功能。

技术选型：
JDK8
MySQL
Spring-boot
Druid
MyBatis
Shiro
Quartz
Freemarker
Fastjson
Thymeleaf
Bootstrap
....

启动：
main方法运行
配置：src/main/resources/application.yml (数据库账号密码)、新建angelblog的数据库并运行项目AngelBlog-web\mysql-db文件夹下angelblog.sql 创建表语句。
idea运行：src/main/java/com/angelblog/AngelblogWebApplication
访问：http://localhost:8086/
后台：http://localhost:8086/admin/
账号：默认管理员账号为 admin/admin123

TIPS: 
如遇到启动失败/切换环境变量后启动失败的,请先maven clean后再尝试启动

QQ交流群：554101646
