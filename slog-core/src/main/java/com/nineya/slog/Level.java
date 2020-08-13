package com.nineya.slog;

/**
 * @author linsongwang
 * @date 2020/7/4 10:27
 */
public enum Level {
    OFF(Integer.MAX_VALUE),
    FATAL(50000),
    ERROR(40000),
    WARN(30000),
    INFO(20000),
    DEBUG(10000),
    TRACE(5000),
    ALL(Integer.MIN_VALUE);

    private int levelNum;

    Level(int levelNum){
        this.levelNum = levelNum;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public static Level value(String operate) {
        for(Level s : values()) {    //values()方法返回enum实例的数组
            if(operate.equalsIgnoreCase(s.name())){
                return s;
            }
        }
        return ALL;
    }
}
