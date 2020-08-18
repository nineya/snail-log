package com.nineya.slog;

import com.nineya.slog.appender.AppenderSkeleton;
import com.nineya.slog.appender.ConsoleAppender;
import com.nineya.slog.config.PropertyConfigurator;
import com.nineya.slog.exception.SnailFileException;
import com.nineya.slog.internal.Node;
import com.nineya.slog.internal.StatusLogger;
import com.nineya.slog.layout.PatternLayout;
import com.nineya.slog.tool.FileTool;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linsongwang
 * @date 2020/7/5 23:34
 */
public final class LogManager {
    public static final String ROOT_LOGGER_NAME = "rootLogger";
    public static final String DEFAULT_CONFIGURATION_FILE = "slog.properties";
    private static final Map<String, Logger> nameLoggers = new ConcurrentHashMap<>();
    private static final Logger STATUS_LOGGER = StatusLogger.getLogger();
    private static LoggerRepository repository = getLoggerRepository();

    public static Logger getLogger(String name) {
        Logger logger = nameLoggers.get(name);
        if (logger == null){
            logger = new Logger(name);
            if (!name.equals(ROOT_LOGGER_NAME)){
                logger.setParent(getRootLogger());
            }
            nameLoggers.put(name, logger);
        }
        return logger;
    }
    public static LoggerRepository getLoggerRepository() {
        if (repository == null) {
            repository = new LoggerRepository(new Node(null, ""));
            try {
                AppenderSkeleton appender = new ConsoleAppender();
                appender.setLayout(new PatternLayout());
                getRootLogger().setAppender(appender);
                new PropertyConfigurator().doConfigure(FileTool.getResourcesStream(DEFAULT_CONFIGURATION_FILE), repository);
            } catch (IOException e) {
                STATUS_LOGGER.error("默认配置文件不存在");
            }
        }
        return repository;
    }

    public static Logger getRootLogger(){
        return getLogger(ROOT_LOGGER_NAME);
    }
}
