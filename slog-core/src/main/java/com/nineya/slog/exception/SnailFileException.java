package com.nineya.slog.exception;

import java.io.IOException;

/**
 * @author linsongwang
 * @date 2020/6/28 1:13
 * 和IO有关的错误封装
 */
public class SnailFileException extends RuntimeException {

    /**
     * 输出错误说明信息和出现错误的文件路径
     * @param message 错误说明信息
     * @param path 出现错误的文件路径
     */
    public SnailFileException(String path, String message){
        super("异常："+message+"，路径："+path);
    }

    /**
     * 输出错误说明信息
     * @param message 错误说明信息
     */
    public SnailFileException(String message){
        super("异常："+message);
    }
}
