package com.nineya.slog.filter;

import java.io.File;
import java.io.IOException;

/**
 * @author linsongwang
 * @date 2020/8/17
 */
public class FilterLogger {
    public static void main(String[] args) throws IOException {
        File directory = new File("");
        System.out.println(FilterLogger.class.getResource("/").getPath());
    }
}
