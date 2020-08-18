package com.nineya.slog;

/**
 * @author linsongwang
 * @date 2020/8/14
 */
public enum  Document {
    ONE_TITLE, TWO_TITLE, THREE_TITLE, FOUR_TITLE, FIVE_TITLE, CONTENT, TABLE, DISO_LIST, ORDE_LIST;

    /**
     * 日志类型中Title分位5个等级，分别用1-5表示，如果输入的数值不是在该范围，抛出异常
     * @param num title级别
     * @return title类型
     */
    public static Document getTitle(int num){
        switch (num){
            case 1:{
                return ONE_TITLE;
            }
            case 2:{
                return TWO_TITLE;
            }
            case 3:{
                return THREE_TITLE;
            }
            case 4:{
                return FOUR_TITLE;
            }
            case 5:{
                return FIVE_TITLE;
            }
        }
        throw new IllegalAccessError("无效的标题级别");
    }
}
