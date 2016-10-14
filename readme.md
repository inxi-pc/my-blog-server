# This my first blog


# Naming Rule:

1. Folder name must be singular, like `model`, `resource`

2. Class name must be singular, and came-case, the first letter must be capitals, like `PostResource`, `Post`

# Myblog Server架构考虑问题列表
```
* 系统分为哪几个层次?
```
总共分四个层次，(交互层)Resource=>Service(服务层)=>Dao(数据库交互层)，Domain Object(领域层)。

```
* 每个层次的职责和边界在哪？
```
Resource: 1. 接受Http请求，定义请求格式，返回格式。2. 验证参数合法性并组装下层调用参数，调用下层服务。
Service: 1. 使用Dao层的服务，并添加必要的逻辑完成一次业务。
Dao: 1.为上层提供数据库的CURD服务。
Domain: 1. 关联数据库表对象，使用setter getter对应表对象的字段约束，比如Not NULL，Default，等。

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





