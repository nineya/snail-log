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
