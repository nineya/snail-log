package com.nineya.slog.appender;

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
