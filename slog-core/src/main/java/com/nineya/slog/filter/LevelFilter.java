package com.nineya.slog.filter;

import com.nineya.slog.Level;
import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/8/1 18:51
 */
public class LevelFilter extends Filter {
    private Level level;

    /**
     * 设置被过滤的最低Level
     * @param level Level类型的level
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * 设置被过滤的最低Level
     * @param level String类型的Level
     */
    public void setLevel(String level) {
        this.level = Level.value(level);
    }

    /**
     * 没有参数的构造方法，用于反射创建Filter
     */
    public LevelFilter(){

    }

    /**
     * 使用level创建Filter
     * @param level Level类型的level
     */
    public LevelFilter(Level level){
        this.level = level;
    }

    /**
     * 当前Filter判断是否通过过滤器的方法。
     * 通过过滤仅表示通过当前Filter的过滤，并不包括nextFilter的过滤
     * @param event 日志消息
     * @return true：通过过滤，false：未通过过滤
     */
    @Override
    protected boolean doDecide(LoggingEvent event) {
        return event.getLevel().getLevelNum() >= level.getLevelNum();
    }
}
