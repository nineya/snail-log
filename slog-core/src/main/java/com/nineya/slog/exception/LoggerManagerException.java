package com.nineya.slog.exception;

/**
 * @author linsongwang
 * @date 2020/7/5 11:39
 * LogManger有关的错误信息封装
 */
public class LoggerManagerException extends Exception {
    /**
     * 输出错误说明信息
     * @param msg 错误说明信息
     */
    public LoggerManagerException(String msg){
        super(msg);
    }
}
