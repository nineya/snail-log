package com.nineya.slog.internal;

import com.nineya.slog.Logger;
import com.nineya.slog.appender.ConsoleAppender;

/**
 * @author linsongwang
 * @date 2020/8/14
 */
public class StatusLogger extends Logger {

    private static final Logger STATUS_LOGGER = new StatusLogger(StatusLogger.class.getName());

    private StatusLogger(String name) {
        super(name);
        setAppender(new ConsoleAppender());
    }

    public static Logger getLogger(){
        return STATUS_LOGGER;
    }
}
