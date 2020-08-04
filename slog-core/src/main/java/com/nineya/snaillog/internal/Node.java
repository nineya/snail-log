package com.nineya.snaillog.internal;

import com.nineya.snaillog.Level;
import com.nineya.snaillog.filter.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linsongwang
 * @date 2020/7/12 11:15
 */
public class Node {
    private  final String nodeName;
    private final Node parent;
    private final Map<String, Node> children = new ConcurrentHashMap<>();
    private final Map<String, Level> levelFilters = new ConcurrentHashMap<>();

    public Node(Node parent, String nodeName){
        this.parent = parent;
        this.nodeName = nodeName;
        if (parent!=null){
            parent.children.put(nodeName, this);
        }
    }

    public Node getChild(String name){
        Node child = children.get(name);
        if (child == null){
            return new Node(this, name);
        }
        return child;
    }

    public void addLevelFilters(String loggerName, Level level){
        levelFilters.put(loggerName, level);
    }

    public Level getLevel(String loggerName){
        return levelFilters.getOrDefault(loggerName, Level.ALL);
    }

    public Node getParent(){
        return parent;
    }
}
