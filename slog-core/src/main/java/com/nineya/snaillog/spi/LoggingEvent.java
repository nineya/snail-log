package com.nineya.snaillog.spi;

import com.nineya.snaillog.Level;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linsongwang
 * @date 2020/7/5 0:28
 */
public class LoggingEvent {
    private final Level level;
    private final String loggerName;
    private final long startTime = System.currentTimeMillis();
    private String threadName;
    private final Object message;
    private String fgcn;

    public LoggingEvent(String fgcn, Level level, String loggerName, Object message) {
        this.level = level;
        this.loggerName = loggerName;
        this.message = message;
        this.fgcn =fgcn;
    }

    public StackTraceElement getLocationInfo(){
        StackTraceElement[] traceElement =  Thread.currentThread().getStackTrace();
        for (int i = traceElement.length - 2; i>=0; i--){
            if (traceElement[i].getClassName().equals(fgcn)){
                return traceElement[i+1];
            }
        }
        return null;
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
}
