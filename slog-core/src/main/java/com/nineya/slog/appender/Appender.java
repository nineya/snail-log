package com.nineya.slog.appender;

import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/7/5 0:23
 */
public interface Appender {
    /**
     * Logger调用该接口实现日志消息输出
     * @param event 封装的日志内容的消息实体
     */
    void callAppend(LoggingEvent event);
}
