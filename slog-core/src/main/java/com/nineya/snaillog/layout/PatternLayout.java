package com.nineya.snaillog.layout;

import com.nineya.snaillog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/8/2 2:48
 */
public class PatternLayout extends Layout {
    private String conversionPattern = "%-d{yyyy-MM-dd HH:mm:ss} [%p]-[Thread: %t]: %m";

    @Override
    public String format(LoggingEvent event) {
        return regexFormat(conversionPattern, event);
    }
}
