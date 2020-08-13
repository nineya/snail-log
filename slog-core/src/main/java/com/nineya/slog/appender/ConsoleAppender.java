package com.nineya.slog.appender;

import com.nineya.slog.Level;
import com.nineya.slog.layout.Layout;
import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/7/5 0:31
 */
public final class ConsoleAppender extends AppenderSkeleton {

    public ConsoleAppender(){
    }

    public ConsoleAppender(Layout layout){
        setLayout(layout);
    }

    @Override
    public void doAppend(LoggingEvent event) {
        if (event.getLevel().getLevelNum() < Level.ERROR.getLevelNum()){
            System.out.println(layout.format(event));
        }else{
            System.err.println(layout.format(event));
        }
    }
}
