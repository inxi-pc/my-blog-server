# Myblog Server技术栈
* Resource: jesery (https://jersey.java.net/documentation/latest/index.html)
* Authenticator & Authority:
JAAS  (http://docs.oracle.com/javase/7/docs/technotes/guides/security/  jaas/JAASRefGuide.html)
JWT (https://jwt.io/introduction/)
* Http server: GrizzlyHttpServer(JNIO)

# Myblog Server架构考虑问题列表
```
* 系统分为哪几个层次?
```
总共分四个层次，(交互层)Resource=>Service(服务层)=>Dao(数据库交互层)，Domain Object(领域层)。

```
* 每个层次的职责和边界在哪？
```
Resource: 1. 定义可接收的HTTP请求格式，返回格式。2. 验证参数合法和有效性(null判断 + invalid函数)并组装下层调用参数，调用下层服务。验证的步骤: *null?(有没有值) ===> isValid?（值合不合法） => set*
Service: 1. 使用Dao层的服务，并添加必要的逻辑完成一次业务。
Dao: 1.为上层提供数据库的CURD服务。
Domain: 1. 关联数据库表对象，表达业务对象的含义。
从resource层开始，要求参数的验证复杂度逐层递减。

```
* 定义函数的时候是否做输入参数的异常判断？输入参数的异常判断是放在调用方还是函数本身？
```
做不做输入参数的异常判断是依据需求来的，但可以从参数是否会造成性能和系统严重错误的角度考虑。依照这个原则，Dao层的参数异常会导致数据同样打开连接，浪费资源，消耗性能，这种情况是应该被阻止的，所以Dao层强制做参数判断。
Service层暂时没考虑清楚。
Resource层需要做参数判断，同样是考虑无效参数将调用下层服务会浪费资源，消耗性能。
参数判断放在函数本身。

```
* 定义函数的时候返回参数的异常判断是放在调用方还是函数本身？
```
基本上放在调用方，如果放在函数本身，那么尽量不要抛出RuntimeException或者做改变返回语义的处理。因为这将使得调用方无法做更多的定制，或者调用方无法拿到准确的语义。


# 遇到的问题列表
1. mybatis工作在事务模式下，事务失败没有自动提交导致死锁
2. mysql触发器获得自增主键值
可用方案：

```sql
  SELECT AUTO_INCREMENT INTO self_id
  FROM information_schema.TABLES
  WHERE TABLE_NAME = 'category' AND TABLE_SCHEMA = database();
```

不可用方案：
LAST_INSERT_ID()，该函数要在insert语句成功后才触发

3. Jackson多出来的字段会被setter设置
情况1: 如果jsoncreator定义2个字段，a, b，当json数据里面传来多余的的字段，
该字段会被setter方法调用设置。

4. Mybatis工作在Pooled模式下，如果连接长时间未活跃，连接将被关闭，导致latest package successful received (time)错误
mysqld配置：
`wait_timeout=28800`
`interactive_timeout=28800`

mybatis配置：
```xml
// belong datasource
<property name="poolPingConnectionsNotUsedFor" value="3600"/>
<property name="poolPingEnabled" value="true"/>
<property name="poolPingQuery" value="SELECT 1"/>
<property name="poolMaximumActiveConnections" value="20"/>
<property name="poolMaximumIdleConnections" value="5"/>
```

注意poolPingConnectionsNotUsedFor要小于 < mysqld.timeout



