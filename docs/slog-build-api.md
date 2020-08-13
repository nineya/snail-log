## 使用Slog生成第一条日志

1. 输入日志记录器名称，创建一个日志记录器，如
```java
Logger logger = Logger.getLogger("sys");
```

2. 除此之外，你还可以使用`Class`创建日志记录器，如
```java
 Logger logger = Logger.getLogger(this.getClass());
```

3. 在`slog`中日志具有FATAL、ERROR、WARN、INFO、DEBUG和TRACEL六种等级，级别依次从高到低，如
```java
logger.fatal("hello slog as fatal.");
logger.error("hello slog as error.");
logger.warn("hello slog as warn.");
logger.info("hello slog as info.");
logger.debug("hello slog as debug.");
logger.trace("hello slog as debug.");
```

4. 在很多时候我们希望把错误的信息通过日志的方式打印出来，`slog`提供了该功能的实现，如
```java
logger.error("hello slog as error.", new Throwable());
```

5. 将参数从具体消息中分离出来，这样有利于消息的阅读，如
```java
logger.info(message, "slog");
```