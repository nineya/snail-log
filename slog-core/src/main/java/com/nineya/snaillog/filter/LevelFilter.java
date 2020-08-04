package com.nineya.snaillog.filter;

import com.nineya.snaillog.Level;
import com.nineya.snaillog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/8/1 18:51
 */
public class LevelFilter extends Filter {
    private final Level level;

    public LevelFilter(Level level){
        this.level = level;
    }

    @Override
    public boolean decide(LoggingEvent event) {
        return event.getLevel().getLevelNum()>level.getLevelNum();
    }
}
