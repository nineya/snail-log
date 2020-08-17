package com.nineya.slog.appender;

import com.nineya.slog.filter.Filter;
import com.nineya.slog.layout.Layout;
import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/8/2 1:33
 */
public abstract class AppenderSkeleton implements Appender {
    protected Layout layout;
    protected Filter headFilter;

    public Filter getHeadFilter() {
        return headFilter;
    }

    public void setFilter(Filter filter){
        this.headFilter = filter;
    }

    public void addFilter(Filter filter) {
        filter.setNextFilter(headFilter);
        this.headFilter = filter;
    }

    @Override
    public void callAppend(LoggingEvent event) {
        if (headFilter==null || headFilter.decide(event)){
            doAppend(event);
        }
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    protected abstract void doAppend(LoggingEvent event);
}
