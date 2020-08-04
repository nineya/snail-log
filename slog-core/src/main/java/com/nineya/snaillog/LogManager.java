package com.nineya.snaillog;

import com.nineya.snaillog.config.PropertyConfigurator;
import com.nineya.snaillog.internal.Node;
import com.nineya.snaillog.tool.FileTool;

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
    private static LoggerRepository repository = getLoggerRepository();

    public static Logger getLogger(String name) {
        Logger logger = nameLoggers.get(name);
        if (logger == null){
            logger = new Logger(name);
            nameLoggers.put(name, logger);
        }
        return logger;
    }
    public static LoggerRepository getLoggerRepository() {
        if (repository == null) {
            repository = new LoggerRepository(new Node(null, ""));
            try {
                new PropertyConfigurator().doConfigure(FileTool.getResourcesPath(DEFAULT_CONFIGURATION_FILE), repository);
            } catch (IOException e) {
            }
        }
        return repository;
    }

    public static Logger getRootLogger(){
        return getLogger(ROOT_LOGGER_NAME);
    }
}
