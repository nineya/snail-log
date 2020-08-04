package com.nineya.snaillog.appender;

import com.nineya.snaillog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/7/5 0:23
 */
public interface Appender {

    void doAppend(LoggingEvent event);
}
