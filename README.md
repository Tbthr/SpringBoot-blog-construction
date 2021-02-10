# 基于Spring Boot开发的个人博客

> **项目链接：[LYQ’s world](47.92.165.44:8080/blog)**



## 1、项目展示

#### 首页展示

![](https://gitee.com/lyq_power/pictures/raw/master/image-20200702164946208.png)

<br>

#### 文章编辑

![image-20201124195155848](https://gitee.com/lyq_power/pictures/raw/master/image-20201124195155848.png)

<br>

#### 后台管理

![image-20201125192348408](https://gitee.com/lyq_power/pictures/raw/master/image-20201125192348408.png)



## 2、项目概述

#### 功能概述

![image-20201125201741334](https://gitee.com/lyq_power/pictures/raw/master/image-20201125201741334.png)

#### 项目背景

对于Springboot的初学者来说，最好的学习方式就是做一个功能较全的项目来练练手，通过自己重构项目来发现其中的潜在难题，并且也能很好的在编码过程中总结、发现问题和解决问题。使用Springboot开发的博客系统，简单实用，适合做练手项目。

#### 功能需求

**主页**

- 博客汇总，以列表形式展示文章，并附上文章作者、发布日期、分类情况、标签情况以及文章简要
- 以分类形式查看文章
- 以标签形式查看文章
- 以时间列表方式归档文章
- 根据关键词查找相关文章
- 个人介绍、联系方式
- 博客网站相关记录
- 友链展示

**后台管理**

- 文章管理
  1. 分页展示文章信息
  2. 增加文章
  3. 对文章进行再编辑以及删除
  4. 根据条件查询文章、自定义排序展示文章列表
- 发布文章
  1. 使用markdown编辑器，支持插入代码，插入图片等功能
  2. 文章可选择分类和标签，以及转载文章支持链接原作者文章
- 分类管理，支持增加、删除、修改分类
- 标签管理，支持增加、删除、修改标签
- 友链管理，支持增加、删除、修改友情链接


#### 安装部署需求

- 使用maven打包war
- 使用Tomcat运行项目
- 购买服务器和域名并配置

## 3、项目设计

#### 总体设计

- **本项目用到的技术、框架、插件**
1. 项目构建：Maven
  2. web框架：Springboot
  3. 数据库ORM：Mybatis
  4. 分页插件：PageHelper
  5. 数据库：MySql
  6. Druid连接池
  7. SL4j日志
  8. 前端模板：Thymeleaf
  9. 前端框架：[Semantic UI](https://semantic-ui.com/)
  10. 文章编辑：[Editor.md](https://pandao.github.io/editor.md)
  11. 内容排版：[ typo.css](https://github.com/sofish/typo.css)
  12. 动画：[animate.css](https://daneden.github.io/animate.css/)
  13. 代码高亮： [prism](https://github.com/PrismJS/prism)
  14. 目录生成： [Tocbot](https://tscanlin.github.io/tocbot/)
  15. 滚动侦测 [waypoints](http://imakewebthings.com/waypoints/)
  16. 平滑滚动 [jquery.scrollTo](https://github.com/flesler/jquery.scrollTo)
  17. 二维码生成 [qrcode.js](https://davidshimjs.github.io/qrcodejs/)
  18. 表格排序 [tablesorter](https://mottie.github.io/tablesorter)
- **本项目中的关键点**
  
  1. 采用Springboot开发，数据库使用orm框架，对于系统的关键业务开启Mybatis二级缓存，加快相应速度。
  
  2. 整体系统采用门户网站+后台管理的方式搭建，门户网站展示博客内容以及博主介绍，后台管理用于编辑文章，编辑分类等。
  
- **环境**
  1. 开发工具：IDEA、Navicat、SourceTree
  2. 语言：JDK1.8、HTML、CSS、JavaScript
  3. 数据库：Mysql8
  4. 项目框架：SSM
  5. ORM：Mybatis
  6. 安全：MD5加密
  7. 缓存：Mybatis二级缓存
  8. 项目构建：Maven
  9. 运行环境：华为云Ubuntu 18.04 server 64bit

#### 结构设计

<img src="https://gitee.com/lyq_power/pictures/raw/master/20180802140136.png" style="zoom:80%;" />

#### 应用分层

<img src="https://gitee.com/lyq_power/pictures/raw/master/image-20200702121942074.png" alt="image-20200702121942074" style="zoom:80%;" />

#### 实体设计

**实体类：**

*  博客 Blog
*  分类 Type
*  标签 Tag
*  评论 Comment
*  用户 User
*  友链 Link
*  友链留言 LinkComment

**实体关系：**

![image-20201125201831744](https://gitee.com/lyq_power/pictures/raw/master/image-20201125201847293.png)

**评论类自关联关系：**

![image-20201125201847293](https://gitee.com/lyq_power/pictures/raw/master/image-20201125201847293.png)

**Blog类：**

<img src="https://gitee.com/lyq_power/pictures/raw/master/image-20200702121749222.png" alt="image-20200702121749222" style="zoom:80%;" />

**Type类：**

![image-20201125201907153](https://gitee.com/lyq_power/pictures/raw/master/image-20201125201907153.png)

**Tag类：**

![image-20201125201919230](https://gitee.com/lyq_power/pictures/raw/master/image-20201125201919230.png)

**Comment类：**

<img src="https://gitee.com/lyq_power/pictures/raw/master/image-20200702121845115.png" alt="image-20200702121845115" style="zoom:80%;" />

**User类：**

<img src="https://gitee.com/lyq_power/pictures/raw/master/image-20200702121904185.png" alt="image-20200702121904185" style="zoom: 80%;" />

#### 数据库设计

> **数据库名：** blog

| 表名                          | 说明             |
| :---------------------------- | :--------------- |
| [blog](#blog)                 | 博客表           |
| [blog_tags](#blog_tags)       | 博客、标签关联表 |
| [comment](#comment)           | 评论表           |
| [link](#link)                 | 友链表           |
| [link_comment](#link_comment) | 留言表           |
| [tag](#tag)                   | 标签表           |
| [type](#type)                 | 分类表           |
| [user](#user)                 | 用户表           |


**表名：** <a id="blog">blog</a>

**数据列：**

| 序号 | 名称            | 数据类型 |    长度    | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :-------------- | :------: | :--------: | :----: | :------: | :--: | :----: | :--: |
|  1   | id              |  bigint  |     19     |   0    |    N     |  Y   |        |      |
|  2   | appreciation    |   bit    |     1      |   0    |    N     |  N   |        |      |
|  3   | commentable     |   bit    |     1      |   0    |    N     |  N   |        |      |
|  4   | content         | longtext | 2147483647 |   0    |    Y     |  N   |        |      |
|  5   | create_time     | datetime |     19     |   0    |    Y     |  N   |        |      |
|  6   | first_picture   | longtext | 2147483647 |   0    |    Y     |  N   |        |      |
|  7   | flag            | varchar  |    255     |   0    |    Y     |  N   |        |      |
|  8   | published       |   bit    |     1      |   0    |    N     |  N   |        |      |
|  9   | recommend       |   bit    |     1      |   0    |    N     |  N   |        |      |
|  10  | share_statement |   bit    |     1      |   0    |    N     |  N   |        |      |
|  11  | title           | varchar  |    255     |   0    |    Y     |  N   |        |      |
|  12  | update_time     | datetime |     19     |   0    |    Y     |  N   |        |      |
|  13  | views           |   int    |     10     |   0    |    Y     |  N   |        |      |
|  14  | type_id         |  bigint  |     19     |   0    |    Y     |  N   |        |      |
|  15  | user_id         |  bigint  |     19     |   0    |    Y     |  N   |        |      |
|  16  | description     | longtext | 2147483647 |   0    |    Y     |  N   |        |      |
|  17  | big_picture     | longtext | 2147483647 |   0    |    Y     |  N   |        |      |


**表名：** <a id="blog_tags">blog_tags</a>

**数据列：**

| 序号 | 名称     | 数据类型 | 长度 | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :------- | :------: | :--: | :----: | :------: | :--: | :----: | :--: |
|  1   | blogs_id |  bigint  |  19  |   0    |    N     |  N   |        |      |
|  2   | tags_id  |  bigint  |  19  |   0    |    N     |  N   |        |      |


**表名：** <a id="comment">comment</a>

**数据列：**

| 序号 | 名称              | 数据类型 | 长度 | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :---------------- | :------: | :--: | :----: | :------: | :--: | :----: | :--: |
|  1   | id                |  bigint  |  19  |   0    |    N     |  Y   |        |      |
|  2   | create_time       | datetime |  19  |   0    |    Y     |  N   |        |      |
|  3   | avatar            | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  4   | email             | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  5   | nickname          | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  6   | content           | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  7   | blog_id           |  bigint  |  19  |   0    |    Y     |  N   |        |      |
|  8   | parent_comment_id |  bigint  |  19  |   0    |    Y     |  N   |        |      |
|  9   | admin_comment     |   bit    |  1   |   0    |    N     |  N   |        |      |


**表名：** <a id="link">link</a>

**数据列：**

| 序号 | 名称        | 数据类型 | 长度 | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :---------- | :------: | :--: | :----: | :------: | :--: | :----: | :--: |
|  1   | id          |  bigint  |  19  |   0    |    N     |  Y   |        |      |
|  2   | friend      |   bit    |  1   |   0    |    N     |  N   |        |      |
|  3   | blog_url    | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  4   | description | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  5   | img_url     | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  6   | name        | varchar  | 255  |   0    |    Y     |  N   |        |      |


**表名：** <a id="link_comment">link_comment</a>

**数据列：**

| 序号 | 名称              | 数据类型 | 长度 | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :---------------- | :------: | :--: | :----: | :------: | :--: | :----: | :--: |
|  1   | id                |  bigint  |  19  |   0    |    N     |  Y   |        |      |
|  2   | admin_comment     |   bit    |  1   |   0    |    N     |  N   |        |      |
|  3   | avatar            | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  4   | content           | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  5   | create_time       | datetime |  19  |   0    |    Y     |  N   |        |      |
|  6   | email             | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  7   | nickname          | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  8   | parent_comment_id |  bigint  |  19  |   0    |    Y     |  N   |        |      |


**表名：** <a id="tag">tag</a>

**数据列：**

| 序号 | 名称 | 数据类型 | 长度 | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :--: | :----: | :------: | :--: | :----: | :--: |
|  1   | id   |  bigint  |  19  |   0    |    N     |  Y   |        |      |
|  2   | name | varchar  | 255  |   0    |    Y     |  N   |        |      |


**表名：** <a id="type">type</a>

**数据列：**

| 序号 | 名称 | 数据类型 | 长度 | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :--: | :----: | :------: | :--: | :----: | :--: |
|  1   | id   |  bigint  |  19  |   0    |    N     |  Y   |        |      |
|  2   | name | varchar  | 255  |   0    |    Y     |  N   |        |      |


**表名：** <a id="user">user</a>

**数据列：**

| 序号 | 名称        | 数据类型 | 长度 | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :---------- | :------: | :--: | :----: | :------: | :--: | :----: | :--: |
|  1   | id          |  bigint  |  19  |   0    |    N     |  Y   |        |      |
|  2   | avatar      | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  3   | create_time | datetime |  19  |   0    |    Y     |  N   |        |      |
|  4   | email       | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  5   | nickname    | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  6   | password    | varchar  | 255  |   0    |    Y     |  N   |        |      |
|  7   | type        |   int    |  10  |   0    |    Y     |  N   |        |      |
|  8   | update_time | datetime |  19  |   0    |    Y     |  N   |        |      |
|  9   | username    | varchar  | 255  |   0    |    Y     |  N   |        |      |

#### 业务设计

**发布文章流程**

<img src="https://gitee.com/lyq_power/pictures/raw/master/20180802141221.png" style="zoom: 67%;" />

**登录流程**

<img src="https://gitee.com/lyq_power/pictures/raw/master/201808021412271.png" style="zoom: 67%;" />



#### 命名约定

**Service/DAO层命名约定：**

*  获取单个对象的方法用get做前缀。
*  获取多个对象的方法用list做前缀。
*  获取统计值的方法用count做前缀。
*  插入的方法用save或insert做前缀。
*  删除的方法用remove或delete做前缀。
*  修改的方法用update做前缀。

## 4、框架搭建

#### 构建与配置

**1、引入Spring Boot模块：**

*  web
*  Thymeleaf
*  Mybatis
*  PageHelper
*  MySQL
*  Druid
*  Lombok
*  Aspects
*  DevTools

**2、application.yml配置**


  	application.yml:

```yaml
server:
  port: 8888
spring:
  profiles:
    active: prod
  banner:
    location: banner.txt
  main:
    banner-mode: console
  thymeleaf:
    cache: false
  mvc:
    favicon:
      enabled: false
  jmx:
    enabled: false

  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      initialSize: 10
      minIdle: 5
      maxActive: 30
      maxWait: 60000
      timeBetweenEvictionRunsMillis: 60000
      minEvictableIdleTimeMillis: 300000
      validationQuery: SELECT 1 FROM DUAL
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      poolPreparedStatements: true
      maxPoolPreparedStatementPerConnectionSize: 20
      filters: stat,wall
      web-stat-filter:
        enabled: true
        url-pattern: /*
        exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
        session-stat-enable: false
        session-stat-max-count: 1000
        principal-cookie-name: admin
        principal-session-name: admin
        profile-enable: true
      stat-view-servlet:
        url-pattern: /druid/*  # 监控页面访问路径
        # 允许清空统计数据
        reset-enable: true
        login-username: admin
        login-password: 123456

mybatis:
  # 配置文件位置
  config-location: classpath:mybatis-config.xml

comment.avatar: /images/fine-1.gif
```

*  数据库连接配置

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/myblog?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT&allowMultiQueries=true
    username: root
    password: ****
```

* 日志配置


​	logback-spring.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!--引入默认的一些设置-->
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    <!--web信息-->
    <logger name="org.springframework.web" level="info"/>

    <!--写入日志到控制台的appender,用默认的,但是要去掉charset,否则windows下tomcat下乱码-->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
        </encoder>
    </appender>

    <!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径-->
    <property name="LOG_PATH" value="D:/Java/JavaProjects/SpringBoot-blog-construction/logs/blog"/>
    <!--写入日志到文件的appender-->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名,每天一个文件-->
            <FileNamePattern>${LOG_PATH}.%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--日志文件保留天数-->
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger : %msg%n</pattern>
        </encoder>
        <!--日志文件最大的大小-->
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>10MB</MaxFileSize>
        </triggeringPolicy>
    </appender>

    <!--异步写日志到文件-->
    <appender name="asyncFileAppender" class="ch.qos.logback.classic.AsyncAppender">
        <discardingThreshold>0</discardingThreshold>
        <queueSize>500</queueSize>
        <appender-ref ref="FILE"/>
    </appender>

    <!--生产环境:打印控制台和输出到文件-->
    <springProfile name="prod">
        <root level="info">
            <appender-ref ref="CONSOLE"/>
            <appender-ref ref="asyncFileAppender"/>
        </root>
    </springProfile>

    <!--开发环境:打印控制台-->
    <springProfile name="dev">
        <!-- 打印sql -->
        <logger name="com.lyq.blog" level="DEBUG"/>
        <root level="DEBUG">
            <appender-ref ref="CONSOLE"/>
        </root>
    </springProfile>

    <!--测试环境:打印控制台-->
    <springProfile name="test">
        <root level="info">
            <appender-ref ref="CONSOLE"/>
        </root>
    </springProfile>
</configuration>
```

*  生产环境与开发环境配置
   *  application-dev.yml
   *  application-prod.yml

#### 异常处理

**1、定义错误页面**

*  404
*  500
*  error

**2、全局处理异常**

```java
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(HttpServletRequest request, Exception e) throws Exception {

        log.error("Request URL : {} , Exception : {}", request.getRequestURL(), e.getMessage());

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("url", request.getRequestURL());
        mav.addObject("exception", e);
        mav.setViewName("error/error");

        return mav;
    }
}
```

错误页面异常信息显示处理：

```html
<div>
    <div th:utext="'&lt;!--'" th:remove="tag"></div>
    <div th:utext="'Failed Request URL : ' + ${url}" th:remove="tag"></div>
    <div th:utext="'Exception message : ' + ${exception.message}" th:remove="tag"></div>
    <ul th:remove="tag">
        <li th:each="st : ${exception.stackTrace}" th:remove="tag">
            <span th:utext="${st}" th:remove="tag">
            </span>
        </li>
    </ul>
    <div th:utext="'--&gt;'" th:remove="tag"></div>
</div>
```

**3、资源找不到异常**

```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExcepiton extends RuntimeException {

    public NotFoundExcepiton() {
    }

    public NotFoundExcepiton(String message) {
        super(message);
    }

    public NotFoundExcepiton(String message, Throwable cause) {
        super(message, cause);
    }
}
```

#### 日志处理

**1、记录日志内容**

*  请求 url
*  访问者 ip
*  调用方法 classMethod
*  参数 args
*  返回内容

**2、记录日志类：**

```java
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.lyq.blog.web.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()
            + "."
            + joinPoint.getSignature().getName();

        RequestLog requestLog = new RequestLog(
            request.getRequestURL().toString(),
            request.getRemoteAddr(),
            classMethod,
            joinPoint.getArgs()
        );
        log.info("Request ----- {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Object result) {
        log.info("Return ------ {}", result);
    }
}
```

#### 页面处理

1、静态页面导入project

2、thymeleaf布局

*  定义fragment
*  使用fragment布局

3、错误页面美化

4、设计与规范

## 5、开发流程

#### 数据库CRUD

- controller层中编写前端接口，接收前端参数
- service层中编写所需业务接口，供controller层调用
- 实现service层中的接口，并注入mapper层中的sql接口
- 采用Mybatis的JavaConfig方式编写Sql语句。由于并没有使用Mybatis的逆向功能，需要自己手写所有sql语句
- 关于事务的实现，在启动类中开启事务，并在service层需要实现事务的业务接口上使用`@Transactional`注解
- 本项目开发并不是很难，但在业务的实现上比较复杂

#### 页面与展示

- 作为一名后端开发，对于css的功力有所欠缺，这里使用了Semantic UI，极大的减少了页面的开发难度
- 前端页面与后端的交互主要是在controller包中，并使用Thymeleaf渲染页面
- 自定义异常处理页面，通过重写`WebMvcConfigurerAdapter`实现自动跳转到404、500页面

#### 网站建设

- 服务器选用的是华为云Ubuntu 18.04 server 64bit
- 域名是阿里云上购买的.xyz的域名
- 网站备案时间较长，按照华为云的流程走大概2周左右时间，需要上传个人身份信息
- 网站配置了安全证书，可实现https访问以及自动从http跳转到https

## 6、后台管理功能实现

#### 登录

**1、构建登录页面和后台管理首页**

**2、UserService和UserRepository**

**3、LoginController实现登录**

**4、MD5加密**

**5、登录拦截器**

#### 分类管理

**1、分类管理页面**

**2、分类列表分页**

**3、分类新增、修改、删除**

#### 标签管理

**1、标签管理页面**

**2、标签列表分页**

**3、标签新增、修改、删除**

#### 博客管理

**1、博客分页查询**

**2、博客新增**

**3、博客修改**

**4、博客删除**

#### 友链管理

**1、友链新增**

**3、友链修改**

**4、友链删除**

## 7、前端展示功能实现

#### 首页展示

**1、博客列表**

**2、top分类**

**3、top标签**

**4、博客详情**

**5、Markdown 转换 HTML** [commonmark-java](https://github.com/atlassian/commonmark-java)

*  pom.xml引用commonmark和扩展插件

```xml
<dependency>
   <groupId>com.atlassian.commonmark</groupId>
   <artifactId>commonmark</artifactId>
   <version>0.10.0</version>
</dependency>
<dependency>
   <groupId>com.atlassian.commonmark</groupId>
   <artifactId>commonmark-ext-heading-anchor</artifactId>
   <version>0.10.0</version>
</dependency>
<dependency>
   <groupId>com.atlassian.commonmark</groupId>
   <artifactId>commonmark-ext-gfm-tables</artifactId>
   <version>0.10.0</version>
</dependency>
```

**6、评论功能**

*  评论信息提交与回复功能
*  评论信息列表展示功能
*  管理员回复评论功能


#### 分类页

#### 标签页

#### 归档页

#### 关于我

## 8、总结

#### 开发中遇到的难点

- 评论的展示，数据库中是多层关系，前端只显示两层，需要处理较繁杂业务逻辑
- 博客编辑页面，使用Editor.md插件，上传图片并不是很便捷，查阅资料和百度，新增了截图自动上传功能
- 最大的难点莫过于前端页面的设计，但使用了Semantic UI后极大的解决了这个问题，只需修改少量css就能实现自己所需的样式

#### 博客网站优缺点

- 最大的一个缺点是，在一些复杂的业务逻辑中，代码耦合性比较高，后期维护和修改较难
- 博客基本功能完善，前端页面较为美观

#### 项目整体优化

- 目前项目首页以及文章页响应时间过长
- 定时定期进行数据库的备份，防止出现网站被攻击后数据丢失的风险
- 写文章部分仅支持插入网络图片，无法从本地上传图片和截图上传（已解决）

#### 未来需增加的功能及待优化

- 加入Redis缓存、微服务架构
- 用户注册、登录功能
- 评论管理、用户管理功能
- 关于我页面的优化
- 博客详情页面的目录生成插件，在目录较多时无法自适应页面
- 博客详情页面排版待优化
