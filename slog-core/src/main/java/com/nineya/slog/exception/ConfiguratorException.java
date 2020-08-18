package com.nineya.slog.exception;

/**
 * @author linsongwang
 * @date 2020/8/14
 * 和 slog Configurator 配置有关错误的封装
 */
public class ConfiguratorException extends RuntimeException {

    /**
     * 输出错误说明信息
     * @param message 错误说明信息
     */
    public ConfiguratorException(String message){
        super("异常："+message);
    }

    /**
     * 输出错误说明信息模板和消息中的具体参数
     * 将错误说明信息的具体参数从message消息中提取出来，作为单独的参数使用
     * @param format 错误说明信息模板
     * @param args 错误说明信息中的参数
     */
    public ConfiguratorException(String format, Object... args){
        super("异常："+String.format(format, args));
    }

    /**
     * 输入错误的caure原因和错误说明信息
     * 将错误说明信息的具体参数从message消息中提取出来，作为单独的参数使用
     * @param cause 错误原因Throwable
     * @param format 错误说明信息模板
     * @param args 错误说明信息中的参数
     */
    public ConfiguratorException(Throwable cause, String format, Object... args) {
        super("异常："+String.format(format, args), cause);
    }

    /**
     * 输入错误的caure原因和错误说明信息
     * @param cause 错误原因Throwable
     * @param message 错误说明信息
     */
    public ConfiguratorException( Throwable cause, String message){
        super("异常："+message, cause);
    }
}
