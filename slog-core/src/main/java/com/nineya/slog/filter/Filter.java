package com.nineya.slog.filter;

import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/7/12 11:20
 */
public abstract class Filter {
    private Filter nextFilter;

    public void setNextFilter(Filter nextFilter){
        this.nextFilter = nextFilter;
    }

    public Filter getNextFilter() {
        return nextFilter;
    }

    /**
     * 执行过滤，遍历所有过滤器
     * @param event 日志信息
     * @return false：未通过过滤，true：通过过滤
     */
    public boolean decide(LoggingEvent event){
        Filter filter = this;
        while (filter != null){
            if (!doDecide(event)){
                return false;
            }
            filter = filter.nextFilter;
        }
        return true;
    }

    /**
     * 实际上过滤器进行过滤的方法，交由继承的子类实现
     * @param event 日志消息
     * @return false：未通过过滤，true：通过过滤
     */
    protected abstract boolean doDecide(LoggingEvent event);
}
