package com.nineya.snaillog;


import com.nineya.snaillog.Level;
import com.nineya.snaillog.Logger;
import com.nineya.snaillog.appender.Appender;
import com.nineya.snaillog.internal.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linsongwang
 * @date 2020/7/7 22:50
 */
public class LoggerRepository {
    private Node rootNode;

    public LoggerRepository(Node rootNode){
        if (rootNode == null){
            throw new NullPointerException("rootNode为null");
        }
        this.rootNode = rootNode;
    }

    public Level levelFilter(String loggerName, String clazz){
        String[] packges = clazz.split("\\.");
        Node node = rootNode;
        Level level = node.getLevel(loggerName);
        for (String s : packges){
            node = node.getChild(s);
            Level l = node.getLevel(loggerName);
            if (l.getLevelNum() > level.getLevelNum()){
                level = l;
            }
        }
        return level;
    }

    public Node getRootNode() {
        return rootNode;
    }
}
