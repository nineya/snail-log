package com.nineya.slog.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linsongwang
 * @date 2020/7/12 12:17
 */
public class StringUtil {
    public static String getTimeFormat(String pattern, long time){
        if (pattern == null || pattern.equals("")){
            return null;
        }
        return new SimpleDateFormat(pattern).format(new Date(time));
    }
}
