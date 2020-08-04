package com.nineya.snaillog.filter;

import com.nineya.snaillog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/7/12 11:20
 */
public abstract class Filter {
    private Filter nextFilter;

    public void setNextFilter(Filter nextFilter){
        this.nextFilter = nextFilter;
    }

    public abstract boolean decide(LoggingEvent event);
}
