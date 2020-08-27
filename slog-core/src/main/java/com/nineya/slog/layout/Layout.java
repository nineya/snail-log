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

    /**
     * 处理消息的接口，交由继承类实现消息处理方法
     * @param event 封装的日志消息内容
     * @return 返回String类型的消息处理结果
     */
    public abstract String format(LoggingEvent event);

    /**
     * conversionPattern参数用于定义输出消息的格式
     * @param conversionPattern 设置conversionPattern
     */
    public void setConversionPattern(String conversionPattern) {
        this.conversionPattern = conversionPattern;
    }

    /**
     * 使用conversionPattern处理日志消息内容，按指定格式输出String类型
     * @param conversionPattern 消息格式模板
     * @param event 消息内容
     * @return 根据模板生成的String类型的消息内容
     */
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

    /**
     * 执行消息内容转换中单独每一项转换的方法
     * @param key 消息转换的类型
     * @param param 每一个转换都可以设置一个String类型的参数
     * @param event 消息内容
     * @return 单独一项内容的转换结果
     */
    protected String doConversion(char key, String param, LoggingEvent event){
        switch (key){
            case 'm':{
                return event.getMessage().toString();
            }
            case 'd':{
                return event.getStartTime(param);
            }
            case 'n':{
                return "\n";
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
