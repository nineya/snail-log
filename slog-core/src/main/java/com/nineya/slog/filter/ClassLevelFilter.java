package com.nineya.slog.filter;

import com.nineya.slog.LogManager;
import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/8/31
 * 该过滤器与Node绑定，通过Node配置的每个包项目的过滤条件进行过滤
 * filterName为Node配置的过滤名称
 */
public class ClassLevelFilter extends Filter {
    private String filterName;

    /**
     * 用于反射创建的默认的构造函数
     */
    public ClassLevelFilter(){

    }

    /**
     * 构造函数
     * @param filterName 过滤名称
     */
    public ClassLevelFilter(String filterName){
        this.filterName = filterName;
    }

    /**
     * 设置FilterName
     * @param filterName 过滤名称
     */
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    /**
     * 进行过滤的方法
     * @param event 日志消息
     * @return true表示通过过滤，false表示未通过过滤
     */
    @Override
    protected boolean doDecide(LoggingEvent event) {
        return event.getLevel().getLevelNum() >=  LogManager.getLoggerRepository().
            levelFilter(this.filterName, event.getLocationInfo().getClassName()).getLevelNum();
    }
}
