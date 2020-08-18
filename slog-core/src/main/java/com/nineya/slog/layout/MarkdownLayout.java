package com.nineya.slog.layout;

import com.nineya.slog.Document;
import com.nineya.slog.spi.LoggingEvent;

/**
 * @author linsongwang
 * @date 2020/8/14
 */
public class MarkdownLayout extends Layout {
    private final static String ONE_TITLE = "# ";
    private final static String TWO_TITLE = "## ";
    private final static String THREE_TITLE = "### ";
    private final static String FOUR_TITLE = "#### ";
    private final static String FIVE_TITLE = "##### ";

    public MarkdownLayout(){
        conversionPattern = "[%-d{yyyy-MM-dd HH:mm:ss} - %p] %m";
    }

    /**
     * 实现父类format方法实现消息转换
     *
     * 在本方法中，只有<u>CONTENT<u/>类型的消息会正常的使用conversionPattern模板进行格式转换，其他类型
     * 的消息都将根据消息具体的类型生成Markdown格式的文本内容。
     *
     * 另外，在本方法中除了<u>CONTENT<u/>类型，其他类型是不会进行异常处理的。
     *
     * @param event 封装的消息内容
     * @return 转换后的String类型消息内容
     */
    @Override
    public String format(LoggingEvent event) {
        switch (event.getDocument()){
            case CONTENT:{
                String format = regexFormat(conversionPattern, event);
                String throwable = event.getThrowableInfo();
                if (throwable == null){
                    return format;
                }
                return format + "\r\n" + throwable;
            }
            case ONE_TITLE:{
                return ONE_TITLE + event.getMessage().toString();
            }
            case TWO_TITLE:{
                return TWO_TITLE + event.getMessage().toString();
            }
            case THREE_TITLE:{
                return THREE_TITLE + event.getMessage().toString();
            }
            case FOUR_TITLE:{
                return FOUR_TITLE + event.getMessage().toString();
            }
            case FIVE_TITLE:{
                return FIVE_TITLE + event.getMessage().toString();
            }
        }
        return null;
    }
}
