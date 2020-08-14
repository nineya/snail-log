package com.nineya.slog.appender;

import com.nineya.slog.spi.LoggingEvent;
import com.nineya.slog.tool.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class SimpleFileAppender extends FileAppender {
    private FileWriter fileWriter;
    private String datePattern;
    private String runTime;

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
        runTime = StringUtil.getTimeFormat(datePattern, System.currentTimeMillis());
    }

    @Override
    public void doAppend(LoggingEvent event) {
        if (fileWriter==null){
            try {
                fileWriter = new FileWriter(new File(fileName), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (runTime != null && !runTime.equals(event.getStartTime(datePattern))){
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
