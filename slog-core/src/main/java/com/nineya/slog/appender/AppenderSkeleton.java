package com.nineya.slog.appender;

import com.nineya.slog.filter.Filter;
import com.nineya.slog.layout.Layout;

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

    public void addFilter(Filter filter) {
        filter.setNextFilter(headFilter);
        this.headFilter = filter;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
