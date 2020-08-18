package com.nineya.slog.appender;

import com.nineya.slog.Level;
import com.nineya.slog.layout.Layout;
import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/7/5 0:31
 */
public final class ConsoleAppender extends AppenderSkeleton {

    /**
     * 必须定义一个不包含任何参数的构造方法，用于反射创建Appender
     */
    public ConsoleAppender(){
    }

    /**
     * 传入Layout创建ConsoleAppender
     * @param layout layout
     */
    public ConsoleAppender(Layout layout){
        setLayout(layout);
    }

    /**
     * 实现日志消息的具体输出
     * 向控制台打印一条日志日志，ERROR级别以下使用<u>System.out<u/>打印，ERROR及以上级别使用
     * <u>System.err<u/>打印日志消息。
     *
     * @param event 日志消息的内容
     */
    @Override
    public void doAppend(LoggingEvent event) {
        if (event.getLevel().getLevelNum() < Level.ERROR.getLevelNum()){
            System.out.println(layout.format(event));
        }else{
            System.err.println(layout.format(event));
        }
    }
}
