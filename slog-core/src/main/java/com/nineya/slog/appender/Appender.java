package com.nineya.slog.appender;

import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/7/5 0:23
 */
public interface Appender {

    void callAppend(LoggingEvent event);
}
