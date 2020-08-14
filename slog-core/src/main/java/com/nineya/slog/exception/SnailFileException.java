package com.nineya.slog.exception;

import java.io.IOException;

/**
 * @author linsongwang
 * @date 2020/6/28 1:13
 */
public class SnailFileException extends IOException {
    public SnailFileException(String path, String message){
        super("异常："+message+"，路径："+path);
    }
    public SnailFileException(String message){
        super("异常："+message);
    }
}
