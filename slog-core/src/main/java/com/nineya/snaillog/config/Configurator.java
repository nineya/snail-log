package com.nineya.snaillog.config;

import com.nineya.snaillog.LoggerRepository;

import java.io.IOException;

/**
 * @author linsongwang
 * @date 2020/7/5 9:45
 */
public interface Configurator<T> {
    void doConfigure(String path, LoggerRepository repository) throws IOException;
    void doConfigure(T t, LoggerRepository repository);
}
