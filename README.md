# snail-log
slog是一个轻量级的日志框架。

## 快速开始

首先，在使用之前你需要引入我们的依赖：
```xml
<dependency>
    <groupId>com.nineya.slog</groupId>
    <artifactId>slog-core</artifactId>
    <version>${SlogCoreVersion}</version>
</dependency>
```

Slog API的基础用法：
```java
package com.nineya.slogtest;

import com.nineya.snaillog.Logger;

public class TestMain {
    private static Logger logger = Logger.getLogger("sys");
    public static void main(String[] args) {
        logger.info("hello logger.");
    }
}
```

示例的`slog.properties`配置文件：
```properties
slog.config.loggerOpen = sys
slog.rootLogger.level = INFO

slog.sys.level=DEBUG
# slog.sys.parent=root
slog.sys.appender=com.nineya.snaillog.appender.ConsoleAppender
slog.sys.appender.attribute.fileName=sys.log
slog.sys.appender.layout=com.nineya.snaillog.layout.PatternLayout
slog.sys.appender.layout.conversionPattern=%-d{yyyy-MM-dd HH:mm:ss} [%p]-[Thread: %t]-[%C.%M()]: %m%n
slog.sys.class.com.nineya.slogtest=OFF
```