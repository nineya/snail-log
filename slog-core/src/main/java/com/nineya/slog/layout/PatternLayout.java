package com.nineya.slog.layout;

import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/8/2 2:48
 */
public class PatternLayout extends Layout {

    /**
     * 基础的format实现，根据conversionPattern模板生成日志消息内容，没有其他附加的操作
     * @param event 封装的日志消息内容
     * @return String的日志消息内容
     */
    @Override
    public String format(LoggingEvent event) {
        String format =  regexFormat(conversionPattern, event);
        String throwable = event.getThrowableInfo();
        if (throwable == null){
            return format;
        }
        return format + "\n" + throwable;
    }
}
