package com.nineya.snaillog.appender;

import com.nineya.snaillog.Level;
import com.nineya.snaillog.layout.Layout;
import com.nineya.snaillog.spi.LoggingEvent;

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
