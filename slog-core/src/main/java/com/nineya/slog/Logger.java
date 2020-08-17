package com.nineya.slog;

import com.nineya.slog.appender.Appender;
import com.nineya.slog.filter.Filter;
import com.nineya.slog.spi.LoggingEvent;

import java.util.List;
import java.util.Map;
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
    private Filter headFilter;
    private static final Pattern pattern = Pattern.compile("\\{\\}");

    public Filter getFilter() {
        return headFilter;
    }

    public void setFilter(Filter filter) {
        this.headFilter = filter;
    }

    public void addFilter(Filter filter){
        filter.setNextFilter(headFilter);
        this.headFilter = filter;
    }

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

    protected Logger(String name){
        this.name = name;
    }

    public void info(Object message){
        forcedLog(Document.CONTENT, Level.INFO, message, null);
    }

    public void info(Object message, Throwable t){
        forcedLog(Document.CONTENT, Level.INFO, message, t);
    }

    public void info(String message, Object... params){
        forcedLog(Document.CONTENT, Level.INFO, disposeMessage(message, params), null);
    }

    public void infoTitle(int tLevel, Object message){
        forcedLog(Document.getTitle(tLevel), Level.INFO, message, null);
    }

    public void infoTitle(int tLevel, String message, Object... params){
        forcedLog(Document.getTitle(tLevel), Level.INFO, disposeMessage(message, params), null);
    }

    public void infoTable(List<Map<String, Object>> table){

    }

    public void error(Object message){
        forcedLog(Document.CONTENT, Level.ERROR, message, null);
    }

    public void error(Object message, Throwable t){
        forcedLog(Document.CONTENT, Level.ERROR, message, t);
    }

    public void error(String message, Object... params){
        forcedLog(Document.CONTENT, Level.ERROR, disposeMessage(message, params), null);
    }

    public void errorTitle(int tLevel, Object message){
        forcedLog(Document.getTitle(tLevel),  Level.ERROR, message, null);
    }

    public void errorTitle(int tLevel, String message, Object... params){
        forcedLog(Document.getTitle(tLevel),  Level.ERROR, disposeMessage(message, params), null);
    }

    public void debug(Object message){
        forcedLog(Document.CONTENT, Level.DEBUG, message, null);
    }

    public void debug(Object message, Throwable t){
        forcedLog(Document.CONTENT, Level.DEBUG, message, t);
    }

    public void debug(String message, Object... params){
        forcedLog(Document.CONTENT, Level.DEBUG, disposeMessage(message, params), null);
    }

    public void debugTitle(int tLevel, Object message){
        forcedLog(Document.getTitle(tLevel),  Level.DEBUG, message, null);
    }

    public void debugTitle(int tLevel, String message, Object... params){
        forcedLog(Document.getTitle(tLevel),  Level.DEBUG, disposeMessage(message, params), null);
    }

    public void warn(Object message){
        forcedLog(Document.CONTENT, Level.WARN, message, null);
    }

    public void warn(Object message, Throwable t){
        forcedLog(Document.CONTENT, Level.WARN, message, t);
    }

    public void warn(String message, Object... params){
        forcedLog(Document.CONTENT, Level.WARN, disposeMessage(message, params), null);
    }

    public void warnTitle(int tLevel, Object message){
        forcedLog(Document.getTitle(tLevel),  Level.WARN, message, null);
    }

    public void warnTitle(int tLevel, String message, Object... params){
        forcedLog(Document.getTitle(tLevel),  Level.WARN, disposeMessage(message, params), null);
    }

    public void fatal(Object message){
        forcedLog(Document.CONTENT, Level.FATAL, message, null);
    }

    public void fatal(Object message, Throwable t){
        forcedLog(Document.CONTENT, Level.FATAL, message, t);
    }

    public void fatal(String message, Object... params){
        forcedLog(Document.CONTENT, Level.FATAL, disposeMessage(message, params), null);
    }

    public void fatalTitle(int tLevel, Object message){
        forcedLog(Document.getTitle(tLevel),  Level.FATAL, message, null);
    }

    public void fatalTitle(int tLevel, String message, Object... params){
        forcedLog(Document.getTitle(tLevel),  Level.FATAL, disposeMessage(message, params), null);
    }

    public void trace(Object message){
        forcedLog(Document.CONTENT, Level.TRACE, message, null);
    }

    public void trace(Object message, Throwable t){
        forcedLog(Document.CONTENT, Level.TRACE, message, t);
    }

    public void trace(String message, Object... params){
        forcedLog(Document.CONTENT, Level.TRACE, disposeMessage(message, params), null);
    }

    public void traceTitle(int tLevel, Object message){
        forcedLog(Document.getTitle(tLevel),  Level.TRACE, message, null);
    }

    public void traceTitle(int tLevel, String message, Object... params){
        forcedLog(Document.getTitle(tLevel),  Level.TRACE, disposeMessage(message, params), null);
    }

    public void log(Document document, Level level, Object message){
        forcedLog(document, level, message, null);
    }

    public void log(Document document, Level level, Object message, Throwable t){
        forcedLog(document, level, message, t);
    }

    public void log(Document document, Level level, String message, Object... params){
        forcedLog(document, level, disposeMessage(message, params), null);
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

    private void forcedLog(Document document, Level level, Object message, Throwable t) {
        if (level.getLevelNum() >= this.level.getLevelNum()){
            LoggingEvent event = new LoggingEvent(FQCN, document, level, name, message, t);
            if ((headFilter == null || headFilter.decide(event)) && level.getLevelNum() >=  LogManager.getLoggerRepository().
                levelFilter(this.name, event.getLocationInfo().getClassName()).getLevelNum()){
                callAppenders(new LoggingEvent(FQCN, document, level, name, message, t));
            }
        }
    }

    private void callAppenders(LoggingEvent event) {
        Logger logger = this;
        while(logger!=null){
            if (logger.appender!=null && LogManager.getLoggerRepository().decide(event)){
                logger.appender.callAppend(event);
            }
            logger = logger.parent;
        }
    }
}
