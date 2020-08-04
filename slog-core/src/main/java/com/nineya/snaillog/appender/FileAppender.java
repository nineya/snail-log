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
public final class FileAppender extends AppenderSkeleton {
    private String fileName;
    private FileWriter fileWriter;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void doAppend(LoggingEvent event) {
        try {
            if (fileWriter==null){
                File file = new File(fileName);
                if(file.exists()){
                    file.createNewFile();
                }
                fileWriter = new FileWriter(file, true);
            }
            fileWriter.write(layout.format(event) + "\r\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
