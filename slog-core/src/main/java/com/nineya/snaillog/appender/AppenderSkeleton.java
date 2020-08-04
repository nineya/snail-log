package com.nineya.snaillog.appender;

import com.nineya.snaillog.filter.Filter;
import com.nineya.snaillog.layout.Layout;

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
