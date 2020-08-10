package com.nineya.snaillog.appender;

import com.nineya.snaillog.spi.LoggingEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class SimpleFileAppender extends FileAppender {
    private FileWriter fileWriter;

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
