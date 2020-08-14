package com.nineya.slog.layout;

import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/8/2 2:48
 */
public class PatternLayout extends Layout {

    public String getConversionPattern() {
        return conversionPattern;
    }

    public void setConversionPattern(String conversionPattern) {
        this.conversionPattern = conversionPattern;
    }

    @Override
    public String format(LoggingEvent event) {
        String format =  regexFormat(conversionPattern, event);
        String throwable = event.getThrowableInfo();
        if (throwable == null){
            return format;
        }
        return format + "\r\n" + throwable;
    }
}
