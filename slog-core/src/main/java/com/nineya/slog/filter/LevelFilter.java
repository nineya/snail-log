package com.nineya.slog.filter;

import com.nineya.slog.Level;
import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/8/1 18:51
 */
public class LevelFilter extends Filter {
    private Level level;

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setLevel(String level) {
        this.level = Level.value(level);
    }

    public LevelFilter(){

    }

    public LevelFilter(Level level){
        this.level = level;
    }

    @Override
    protected boolean doDecide(LoggingEvent event) {
        return event.getLevel().getLevelNum() >= level.getLevelNum();
    }
}
