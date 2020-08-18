package com.nineya.slog.filter;

import com.nineya.slog.LogManager;
import com.nineya.slog.Logger;
import com.nineya.slog.config.PropertyConfigurator;
import com.nineya.slog.tool.FileTool;

/**
 * @author linsongwang
 * @date 2020/8/18
 * Filter有关的应用
 * 通过<u>LevelFilter</u>过滤器对全局过滤器、Logger过滤器、Appender过滤器做的简单的运用示例
 */
public class FilterLogger {
    private static Logger rootLogger = LogManager.getRootLogger();
    static {
        PropertyConfigurator.configure(FileTool.getResourcesStream("filter-logger.properties"));
    }
    public static void globalFilter(){
        // 全局过滤器定义的级别为debug，低于这个级别的日志都不能被打印
        rootLogger.trace("被过滤的日志");
        rootLogger.debug("1. 正常打印日志debug");
        rootLogger.info("2. 正常打印日志info");
    }

    public static void loggerFilter(){
        Logger logger = Logger.getLogger("logfilter");
        logger.info("被logger filter过滤的日志");
        logger.warn("1. 正常打印日志warn");
        logger.error("2. 正常打印日志error");
    }

    public static void appenderFilter(){
        Logger logger = Logger.getLogger("appfilter");
        logger.error("1. 正常打印日志error");
        logger.warn("被Appender过滤的日志,rootLogger可以正常打印");
        logger.fatal("2. 正常打印日志fatal");
    }

    public static void main(String[] args) {
        rootLogger.info("全局过滤器");
        globalFilter();
        rootLogger.info("Logger过滤器");
        loggerFilter();
        rootLogger.info("Appender过滤器");
        appenderFilter();
    }
}
