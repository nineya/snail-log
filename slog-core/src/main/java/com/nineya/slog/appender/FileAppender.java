package com.nineya.slog.appender;

import com.nineya.slog.tool.StringUtil;

import java.io.File;

/**
 * @author linsongwang
 * @date 2020/8/2 10:54
 */
public abstract class FileAppender extends AppenderSkeleton {
    protected String fileName;
    protected String datePattern;
    protected String runTime;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        if (datePattern != null){
            buildRunTime();
        }
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
        if (fileName != null){
            buildRunTime();
        }
    }

    private void buildRunTime(){
        File file = new File(fileName);
        long time = System.currentTimeMillis();
        if (file.exists()){
            time = file.lastModified();
        }
        runTime = StringUtil.getTimeFormat(datePattern, time);
    }

    /**
     * 判断是否超时
     * @param time 时间
     * @return 是否超过datePattern的时间段
     */
    protected boolean isMorePattern(long time){
        return runTime != null && !runTime.equals(StringUtil.getTimeFormat(datePattern, time));
    }
}
