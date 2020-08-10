package com.nineya.snaillog.appender;

import com.nineya.snaillog.spi.LoggingEvent;
import com.nineya.snaillog.tool.FileTool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author linsongwang
 * @date 2020/8/2 10:54
 */
public abstract class FileAppender extends AppenderSkeleton {
    protected String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
