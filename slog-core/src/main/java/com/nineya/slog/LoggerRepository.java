package com.nineya.slog;


import com.nineya.slog.filter.Filter;
import com.nineya.slog.internal.Node;
import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/7/7 22:50
 * 存储一些公共信息的实体类
 */
public class LoggerRepository {
    private Node rootNode;
    private Filter filter;

    /**
     * 使用rootNode实例化
     * @param rootNode 根节点
     */
    public LoggerRepository(Node rootNode){
        if (rootNode == null){
            throw new NullPointerException("rootNode为null");
        }
        this.rootNode = rootNode;
    }

    /**
     * 取得指定包名的filterName的Level过滤级别
     * @param filterName Level绑定的过滤名称
     * @param clazz 包名
     * @return Level过滤级别
     */
    public Level levelFilter(String filterName, String clazz){
        String[] packges = clazz.split("\\.");
        Node node = rootNode;
        Level level = node.getLevel(filterName);
        for (String s : packges){
            node = node.getChild(s);
            Level l = node.getLevel(filterName);
            if (l.getLevelNum() > level.getLevelNum()){
                level = l;
            }
        }
        return level;
    }

    /**
     * 取得rootNode
     * @return rootNode
     */
    public Node getRootNode() {
        return rootNode;
    }

    /**
     * 设置全局过滤器Filter
     * @param filter 全局过滤器链的首个过滤器
     */
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    /**
     * 取得全局过滤器链的首个过滤器
     * @return 全局过滤器链的首个过滤器
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * 是否通过过滤器过滤
     * @param event 消息内容
     * @return true：通过过滤，False：拦截
     */
    public boolean decide(LoggingEvent event){
        if (filter == null){
            return true;
        }
        return filter.decide(event);
    }
}
