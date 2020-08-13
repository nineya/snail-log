package com.nineya.slog.filter;

import com.nineya.slog.Level;
import com.nineya.slog.spi.LoggingEvent;

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
