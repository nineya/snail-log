package com.nineya.slog.spi;

import com.nineya.slog.Document;
import com.nineya.slog.Level;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linsongwang
 * @date 2020/7/5 0:28
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

    public LoggingEvent(String fgcn,Document document, Level level, String loggerName, Object message, Throwable throwable) {
        this.document = document;
        this.level = level;
        this.loggerName = loggerName;
        this.message = message;
        this.locationInfo = getLocationInfo(fgcn);
        this.throwable = throwable;
    }

    private StackTraceElement getLocationInfo(String fgcn){
        StackTraceElement[] traceElement =  Thread.currentThread().getStackTrace();
        for (int i = traceElement.length - 2; i>=0; i--){
            if (traceElement[i].getClassName().equals(fgcn)){
                return traceElement[i+1];
            }
        }
        return null;
    }

    public StackTraceElement getLocationInfo(){
        return locationInfo;
    }

    public Level getLevel() {
        return level;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public String getStartTime(String pattern) {
        if (pattern == null || pattern.equals("")){
            pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        return new SimpleDateFormat(pattern).format(new Date(startTime));
    }

    public String getThreadName() {
        if (threadName==null){
            threadName = Thread.currentThread().getName();
        }
        return threadName;
    }

    public Object getMessage() {
        return message;
    }

    public Document getDocument() {
        return document;
    }

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
