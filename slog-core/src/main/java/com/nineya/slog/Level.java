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

    /**
     * 取得当前Level等级对应的LevelNum
     * @return level对应的值
     */
    public int getLevelNum() {
        return levelNum;
    }

    /**
     * 将String的level转换为Level枚举类型，不区分大小写
     * @param operate String的level
     * @return Level枚举类型
     */
    public static Level value(String operate) {
        for(Level s : values()) {    //values()方法返回enum实例的数组
            if(operate.equalsIgnoreCase(s.name())){
                return s;
            }
        }
        return ALL;
    }
}
