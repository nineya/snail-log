package com.nineya.slog.internal;

import com.nineya.slog.Logger;
import com.nineya.slog.appender.ConsoleAppender;

/**
 * @author linsongwang
 * @date 2020/8/14
 * Slog内部用的Logger，用于输出Logger状态，或者一些错误信息，没有添加到LogManager中，不受配置的影响。
 * StatusLogger是继承于Logger的。
 */
public class StatusLogger extends Logger {

    private static final Logger STATUS_LOGGER = new StatusLogger(StatusLogger.class.getName());

    /**
     * 实例化StatusLogger
     * @param name
     */
    private StatusLogger(String name) {
        super(name);
        setAppender(new ConsoleAppender());
    }

    public static Logger getLogger(){
        return STATUS_LOGGER;
    }
}
