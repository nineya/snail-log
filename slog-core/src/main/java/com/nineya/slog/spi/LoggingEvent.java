package com.nineya.slog.spi;

import com.nineya.slog.Document;
import com.nineya.slog.Level;
import com.nineya.slog.tool.StringUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linsongwang
 * @date 2020/7/5 0:28
 * Logger日志消息封装的实体类
 */
public class LoggingEvent {
    private final Document document;
    private final Level level;
    private final String loggerName;
    private final long startTime = System.currentTimeMillis();
    private String threadName;
    private final Object message;
    private StackTraceElement locationInfo;
    private Throwable throwable;

    /**
     * 构造方法
     * @param fgcn Logger的包名
     * @param document 日志消息的类型，Content、table等等
     * @param level 日志消息的Level级别
     * @param loggerName Logger日志记录器的名称
     * @param message 消息内容
     * @param throwable 本条消息是否要打印错误信息，错误信息内容
     */
    public LoggingEvent(String fgcn,Document document, Level level, String loggerName, Object message, Throwable throwable) {
        this.document = document;
        this.level = level;
        this.loggerName = loggerName;
        this.message = message;
        this.locationInfo = getLocationInfo(fgcn);
        this.throwable = throwable;
    }

    /**
     * 通过Logger的包名，找到调用Logger进行日志打印的的类和方法名等信息。
     * 在初始化时被调用
     * @param fgcn Logger的包名
     * @return StackTraceElement
     */
    private StackTraceElement getLocationInfo(String fgcn){
        StackTraceElement[] traceElement =  Thread.currentThread().getStackTrace();
        for (int i = traceElement.length - 2; i>=0; i--){
            if (traceElement[i].getClassName().equals(fgcn)){
                return traceElement[i+1];
            }
        }
        return null;
    }

    /**
     *取得调用Logger进行日志打印的的类和方法名等信息。
     * @return StackTraceElement
     */
    public StackTraceElement getLocationInfo(){
        return locationInfo;
    }

    /**
     * 取得日志的Level级别
     * @return Level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * 取得Logger日志记录器的名称
     * @return 日志记录器的名称
     */
    public String getLoggerName() {
        return loggerName;
    }

    /**
     * 取得日志消息创建的时间，使用pattern模板转换成String类似输出
     * @param pattern pattern模板
     * @return String的时间
     */
    public String getStartTime(String pattern) {
        if (pattern == null || pattern.equals("")){
            pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        return StringUtil.getTimeFormat(pattern, startTime);
    }

    /**
     * 取得日志消息创建的时间戳
     * @return 时间戳
     */
    public Long getTimeStamp(){
        return startTime;
    }

    /**
     * 取得创建日志消息线程的线程名
     * @return 线程名
     */
    public String getThreadName() {
        if (threadName==null){
            threadName = Thread.currentThread().getName();
        }
        return threadName;
    }

    /**
     * 取得Message消息内容
     * @return 消息内容
     */
    public Object getMessage() {
        return message;
    }

    /**
     * 取得日志消息类型
     * @return 消息类型
     */
    public Document getDocument() {
        return document;
    }

    /**
     * 取得Throwable错误信息内容
     * @return 错误信息内容
     */
    public String getThrowableInfo(){
        if (throwable == null){
            return null;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.flush();
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
