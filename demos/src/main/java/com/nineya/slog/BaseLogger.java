package com.nineya.slog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linsongwang
 * @date 2020/6/21 16:12
 */
public class BaseLogger {
    private static Logger logger = Logger.getLogger("sys");

    public static void baseLogger(){
        Logger rootLogger = LogManager.getRootLogger();
        rootLogger.fatal("hello slog as fatal.");
        rootLogger.error("hello slog as error.");
        rootLogger.warn("hello slog as warn.");
        rootLogger.info("hello slog as info.");
        rootLogger.debug("hello slog as debug.");
        rootLogger.trace("hello slog as debug.");
    }

    public static void titleContent(){
        logger.infoTitle(1, "know slog");
        logger.infoTitle(2, "know slog");
        logger.infoTitle(3, "know slog");
        logger.infoTitle(4, "know slog");
        logger.infoTitle(5, "know slog");
    }

    public static void paramsLogger(){
        String message = "hello {}.";
        logger.info(message, "slog");
    }

    public static void throwableLogger(){
        logger.error("error: 包含错误信息如下", new Throwable());
    }

    public static void filterLogger(){
        // 正常输出
        logger.fatal("hello slog as fatal.");
        logger.error("hello slog as error.");

        // 不满足Logger LevelFilter过滤条件
        logger.info("info：不满足sys Logger LevelFilter过滤条件");

        // 不满足Appender LevelFilter过滤条件
        logger.warn("warn：不满足sys Appender LevelFilter过滤条件");
    }

    public static void main(String[] args) {
        logger.info("基础Logger使用");
        baseLogger();
        logger.info("Logger标题");
        titleContent();
        logger.info("Logger参数使用");
        paramsLogger();
        logger.info("throwable错误打印");
        throwableLogger();
        logger.info("过滤器使用");
        filterLogger();
    }
}
