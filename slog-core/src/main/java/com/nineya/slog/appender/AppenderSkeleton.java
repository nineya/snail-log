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

    /**
     * 取得首个<u>Filter<u/>对象
     * @return 首个<u>Filter<u/>对象
     */
    public Filter getHeadFilter() {
        return headFilter;
    }

    /**
     * 设置首个<u>Filter<u/>对象
     * @param filter 首个<u>Filter<u/>对象
     */
    public void setFilter(Filter filter){
        this.headFilter = filter;
    }

    /**
     * 添加<u>Filter<u/>对象，添加的filter将被作为headFilter，而原本的headFilter将作为filter的
     * NextFilter，由此形成一个链式的结构
     *
     * @param filter 首个<u>Filter<u/>对象
     */
    public void addFilter(Filter filter) {
        filter.setNextFilter(headFilter);
        this.headFilter = filter;
    }

    /**
     * 请求执行日志消息输出
     * 在本方法中将根据过滤器进行过滤，如果通过了<u>Appender Filter<u/>的过滤，调用doAppend方法
     * 进行日志内容的打印
     *
     * @param event 封装的日志内容的消息实体
     */
    @Override
    public void callAppend(LoggingEvent event) {
        if (headFilter==null || headFilter.decide(event)){
            doAppend(event);
        }
    }

    /**
     * 取得Layout
     * @return Layout
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * 设置Layout，该方法还用于Layout属性的注入
     * @param layout Layout
     */
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    /**
     * 交由子类来具体实现，定义输出日志消息的具体方法
     * @param event 日志消息的内容
     */
    protected abstract void doAppend(LoggingEvent event);
}
