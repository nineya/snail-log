package com.nineya.slog.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linsongwang
 * @date 2020/7/12 12:17
 */
public class StringUtil {
    /**
     * 根据pattern模板将时间戳转换为String类型时间
     * @param pattern 模板
     * @param time 时间戳
     * @return String类型时间
     */
    public static String getTimeFormat(String pattern, long time){
        if (pattern == null || pattern.equals("")){
            return null;
        }
        return new SimpleDateFormat(pattern).format(new Date(time));
    }
}
