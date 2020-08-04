package com.nineya.snaillog;

import com.nineya.snaillog.appender.Appender;
import com.nineya.snaillog.spi.LoggingEvent;

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

    public void error(Object message){

    }

    public void debug(Object message){

    }

    public void warn(Object message){

    }

    public void fatal(Object message){

    }

    public void trace(Object message){

    }

    public static Logger getLogger(String name){
        return LogManager.getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz){
        return LogManager.getLogger(clazz.getName());
    }

    private void forcedLog(Level level, Object message, Throwable t) {
        callAppenders(new LoggingEvent(FQCN, level, name, message));
        if (level.getLevelNum() > this.level.getLevelNum()){
            LoggingEvent event = new LoggingEvent(FQCN, level, name, message);
            if (LogManager.getLoggerRepository().levelFilter(name, event.getLocationInfo().getClassName())
                    .getLevelNum() <= level.getLevelNum()){
            }
        }
    }

    private void callAppenders(LoggingEvent event) {
        for (Logger logger = this; logger != null; logger = logger.parent){
            if (logger.appender!=null && event.getLevel().getLevelNum() >= logger.level.getLevelNum() && event.getLevel().getLevelNum()
                    >= LogManager.getLoggerRepository().levelFilter(logger.name, event.getLocationInfo().getClassName()).getLevelNum()){
                logger.appender.doAppend(event);
            }
        }
        Logger rootLogger = LogManager.getRootLogger();
        if (event.getLevel().getLevelNum() >= rootLogger.level.getLevelNum() && event.getLevel().getLevelNum()
                >= LogManager.getLoggerRepository().levelFilter(rootLogger.name, event.getLocationInfo().getClassName()).getLevelNum()){
            rootLogger.appender.doAppend(event);
        }
    }
}
