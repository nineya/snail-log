package com.nineya.slog.exception;

import java.io.IOException;

/**
 * @author linsongwang
 * @date 2020/6/28 1:13
 */
public class SnailFileException extends IOException {
    public SnailFileException(String path, String message){
        super("路径="+path+"，出现异常="+message);
    }
}
