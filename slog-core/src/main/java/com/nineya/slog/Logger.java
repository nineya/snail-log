package com.nineya.slog;

import com.nineya.slog.appender.Appender;
import com.nineya.slog.spi.LoggingEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author linsongwang
 * @date 2020/7/5 23:20
 */
public class Logger {
    private final String name;
    private Level level = Level.INFO;
    private Logger parent;
    private Appender appender;
    private static final String FQCN = Logger.class.getName();
    private static final Pattern pattern = Pattern.compile("\\{\\}");

    public Appender getAppender() {
        return appender;
    }

    public void setAppender(Appender appender) {
        this.appender = appender;
    }

    public Logger getParent() {
        return parent;
    }

    public void setParent(Logger parent) {
        this.parent = parent;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    Logger(String name){
        this.name = name;
    }

    public void info(Object message){
        forcedLog(Level.INFO, message, null);
    }

    public void info(Object message, Throwable t){
        forcedLog(Level.INFO, message, t);
    }

    public void info(String message, Object... params){
        forcedLog(Level.INFO, disposeMessage(message, params), null);
    }

    public void error(Object message){
        forcedLog(Level.ERROR, message, null);
    }

    public void error(Object message, Throwable t){
        forcedLog(Level.ERROR, message, t);
    }

    public void error(String message, Object... params){
        forcedLog(Level.INFO, disposeMessage(message, params), null);
    }

    public void debug(Object message){
        forcedLog(Level.DEBUG, message, null);
    }

    public void debug(Object message, Throwable t){
        forcedLog(Level.DEBUG, message, t);
    }

    public void debug(String message, Object... params){
        forcedLog(Level.DEBUG, disposeMessage(message, params), null);
    }

    public void warn(Object message){
        forcedLog(Level.WARN, message, null);
    }

    public void warn(Object message, Throwable t){
        forcedLog(Level.WARN, message, t);
    }

    public void warn(String message, Object... params){
        forcedLog(Level.WARN, disposeMessage(message, params), null);
    }

    public void fatal(Object message){
        forcedLog(Level.FATAL, message, null);
    }

    public void fatal(Object message, Throwable t){
        forcedLog(Level.FATAL, message, t);
    }

    public void fatal(String message, Object... params){
        forcedLog(Level.FATAL, disposeMessage(message, params), null);
    }

    public void trace(Object message){
        forcedLog(Level.TRACE, message, null);
    }

    public void trace(Object message, Throwable t){
        forcedLog(Level.TRACE, message, t);
    }

    public void trace(String message, Object... params){
        forcedLog(Level.TRACE, disposeMessage(message, params), null);
    }

    private String disposeMessage(String message, Object[] params){
        Matcher matcher = pattern.matcher(message);
        StringBuilder sb = new StringBuilder(message);
        int i = 0;
        while (matcher.find()){
            String group = matcher.group();
            if (i >= params.length){
                new IllegalAccessError("缺少 params ！");
            }
            sb.replace(matcher.start(), matcher.end(), params[i++].toString());
        }
        if (i != params.length){
            new IllegalAccessError("params 数量不对应！");
        }
        return sb.toString();
    }

    public static Logger getLogger(String name){
        return LogManager.getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz){
        return LogManager.getLogger(clazz.getName());
    }

    private void forcedLog(Level level, Object message, Throwable t) {
        if (level.getLevelNum() >= level.getLevelNum()){
            callAppenders(new LoggingEvent(FQCN, level, name, message, t));
        }
    }

    private void callAppenders(LoggingEvent event) {
        Logger logger = this;
        while(logger!=null && event.getLevel().getLevelNum() > logger.level.getLevelNum()){
            if (logger.appender!=null && event.getLevel().getLevelNum() >=  LogManager.getLoggerRepository()
                .levelFilter(logger.name, event.getLocationInfo().getClassName()).getLevelNum()){
                logger.appender.doAppend(event);
            }
            logger = logger.parent;
        }
        // 执行rootLogger的appender
        Logger rootLogger = LogManager.getRootLogger();
        if (rootLogger.appender!=null && event.getLevel().getLevelNum() >= rootLogger.level.getLevelNum() && event.getLevel().getLevelNum()
                >= LogManager.getLoggerRepository().levelFilter(rootLogger.name, event.getLocationInfo().getClassName()).getLevelNum()){
            rootLogger.appender.doAppend(event);
        }
    }
}
