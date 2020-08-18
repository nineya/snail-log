package com.nineya.slog.appender;

import com.nineya.slog.tool.StringUtil;

import java.io.File;

/**
 * @author linsongwang
 * @date 2020/8/2 10:54
 * 向文件中打印日志的抽象Appender
 */
public abstract class FileAppender extends AppenderSkeleton {
    protected String fileName;
    protected String datePattern;
    protected String runTime;

    /**
     * 取得fileName
     * 如果日志文件要输出在jar同级路径，fileName为文件名
     * 否则fileName为文件的具体路径或相对路径
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置fileName
     * 如果日志文件要输出在jar同级路径，fileName为文件名
     * 否则fileName为文件的具体路径或相对路径
     * 如果datePattern已经定义，则调用<u>buildRunTime<u/>生成RunTime。
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
        if (datePattern != null){
            buildRunTime();
        }
    }

    /**
     * 设置日志按时间的保存格式，将按DatePattern将不同时段的日志文件分开存储。
     * 如果fileName已经定义，则调用<u>buildRunTime<u/>生成RunTime。
     * @param datePattern String类型的时间格式模板
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
        if (fileName != null){
            buildRunTime();
        }
    }

    /**
     * 生成RunTime时间
     * 该方法在<u>setFileName<u/>h或<u>setDatePattern</u>,之后被调用，且fileName和
     * datePattern必须都已经设置了值
     * 如果fileName对应的文件已经存在，则使用该文件最后一次修改时间作为时间戳生成RunTime，
     * 如果未见不存在，则使用当前时间的时间戳作为RunTime。
     *
     */
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
