package com.nineya.slog.appender;

import com.nineya.slog.spi.LoggingEvent;
import com.nineya.slog.tool.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class SimpleFileAppender extends FileAppender {
    private FileWriter fileWriter;

    /**
     * 必须定义一个不包含任何参数的构造方法，用于反射创建Appender
     */
    public SimpleFileAppender(){
    }

    /**
     * 输入fileName，创建Appender
     * @param fileName
     */
    public SimpleFileAppender(String fileName){
        this.fileName = fileName;
    }

    /**
     * 实现向文件中打印日志消息
     * 在打印之前先判断日志消息是否已经过了RunTime时间线，如果超过时间线将旧日志按runtime保存为
     * 一份新的文件，文件名为<u>fileName+Runtime</u>，并更新runTime时间。
     *
     * @param event 日志消息的内容
     */
    @Override
    public void doAppend(LoggingEvent event) {
        if (fileWriter==null){
            try {
                fileWriter = new FileWriter(new File(fileName), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (isMorePattern(event.getTimeStamp())){
            File oldfile = new File(fileName);
            File newfile = new File(fileName + runTime);
            int i = 0;
            while (newfile.exists()){
                newfile = new File(fileName + "(" + ++i + ")" + runTime);
            }
            try {
                fileWriter.close();
                oldfile.renameTo(newfile);
                fileWriter = new FileWriter(new File(fileName), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            runTime = event.getStartTime(datePattern);
        }
        try {
            fileWriter.write(layout.format(event) + "\r\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
