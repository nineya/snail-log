package com.nineya.slog.internal;

import com.nineya.slog.Level;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linsongwang
 * @date 2020/7/12 11:15
 * 包节点，将包路径按<b>.<b/>分割为一个列表，以链路的方式存储下来
 */
public class Node {
    private  final String nodeName;
    private final Node parent;
    private final Map<String, Node> children = new ConcurrentHashMap<>();
    private final Map<String, Level> levelFilters = new ConcurrentHashMap<>();

    /**
     * 通过父Node和nodename创建一个Node
     * @param parent Node的父节点，除了rootNode，其他Node都具有父节点
     * @param nodeName node名称
     */
    public Node(Node parent, String nodeName){
        this.parent = parent;
        this.nodeName = nodeName;
        if (parent!=null){
            parent.children.put(nodeName, this);
        }
    }

    /**
     * 取得当前Node的子节点
     * @param name 子节点名称
     * @return 返回子节点Node
     */
    public Node getChild(String name){
        Node child = children.get(name);
        if (child == null){
            return new Node(this, name);
        }
        return child;
    }

    /**
     * 当前节点添加一条属于filterName的Level过滤
     * @param filterName Level过滤绑定的名称，可能是Logger名称，或者一个指定的其他名称
     * @param level Level
     */
    public void addLevelFilters(String filterName, Level level){
        levelFilters.put(filterName, level);
    }

    /**
     * 取得当前节点filterName指定的Level
     * @param filterName 过滤名称
     * @return Level
     */
    public Level getLevel(String filterName){
        return levelFilters.getOrDefault(filterName, Level.ALL);
    }

    /**
     * 取得父节点
     * @return 父节点Node
     */
    public Node getParent(){
        return parent;
    }
}
