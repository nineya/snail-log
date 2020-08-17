package com.nineya.slog.exception;

/**
 * @author linsongwang
 * @date 2020/8/14
 */
public class ConfiguratorException extends RuntimeException {

    public ConfiguratorException(String message){
        super("异常："+message);
    }

    public ConfiguratorException(Throwable cause, String format, Object... args) {
        super(String.format(format, args), cause);
    }

    public ConfiguratorException( Throwable cause, String message){
        super("异常："+message, cause);
    }
}
