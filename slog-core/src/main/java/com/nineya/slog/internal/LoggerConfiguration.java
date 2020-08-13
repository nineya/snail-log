package com.nineya.slog.internal;

import com.nineya.slog.appender.Appender;
import com.nineya.slog.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linsongwang
 * @date 2020/8/1 16:34
 * loggger配置
 */
public class LoggerConfiguration {
    private String loggerName;
    private Map<Class, Level> configuration = new HashMap<>();
    private List<Appender> appenders = new ArrayList<Appender>();

    public LoggerConfiguration(String loggerName){
        this.loggerName = loggerName;
    }

    public void addConfiguration(Class<?> clazz, Level level){
        configuration.put(clazz, level);
    }

    public void addFileAppend(Appender appender){
        appenders.add(appender);
    }

}
