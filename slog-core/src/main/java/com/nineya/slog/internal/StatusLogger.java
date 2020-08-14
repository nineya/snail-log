package com.nineya.slog.internal;

import com.nineya.slog.Logger;

/**
 * @author linsongwang
 * @date 2020/8/14
 */
public class StatusLogger extends Logger {

    private static final Logger STATUS_LOGGER = new StatusLogger(StatusLogger.class.getName());

    private StatusLogger(String name) {
        super(name);
    }

    public static Logger getLogger(){
        return STATUS_LOGGER;
    }
}
