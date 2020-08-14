package com.nineya.slog.layout;

import com.nineya.slog.spi.LoggingEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author linsongwang
 * @date 2020/8/2 1:34
 */
public abstract class Layout {
    protected String conversionPattern = "%-d{yyyy-MM-dd HH:mm:ss} [%p]-[Thread: %t]: %m";
    private static final Pattern REGULAR = Pattern.compile("%(-[a-zA-Z]\\{[^}]*\\}|%|[a-zA-Z])");

    public abstract String format(LoggingEvent event);

    protected String regexFormat(String conversionPattern, LoggingEvent event){
        Matcher matcher = REGULAR.matcher(conversionPattern);
        while (matcher.find()){
            String group = matcher.group();
            String conversion = null;
            if (group.length() == 2){
                conversion = doConversion(group.charAt(1), null, event);
            }else{
                conversion = doConversion(group.charAt(2), group.substring(4, group.length()-  1), event);
            }
            if (conversion!=null){
                conversionPattern = conversionPattern.replace(group, conversion);
            }
        }
        return conversionPattern;
    }

    protected String doConversion(char key, String param, LoggingEvent event){
        switch (key){
            case 'm':{
                return event.getMessage().toString();
            }
            case 'd':{
                return event.getStartTime(param);
            }
            case 'n':{
                return "\r\n";
            }
            case 'C':{
                StackTraceElement element = event.getLocationInfo();
                if (element!=null){
                    return event.getLocationInfo().getClassName();
                }
                return null;
            }
            case 'M':{
                StackTraceElement element = event.getLocationInfo();
                if (element!=null){
                    return event.getLocationInfo().getMethodName();
                }
                return null;
            }
            case 'p':{
                return event.getLevel().name();
            }
            case 't':{
                return event.getThreadName();
            }
            case '%':{
                return "%";
            }
        }
        return null;
    }
}
