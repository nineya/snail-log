package com.nineya.slog.config;

import com.nineya.slog.LoggerRepository;

import java.io.IOException;

/**
 * @author linsongwang
 * @date 2020/7/5 9:45
 */
public interface Configurator<T> {
    /**
     * 通过配置文件路径来生成配置信息
     * @param path 配置文件路径
     * @param repository 一些公共配置信息存储的对象
     * @throws IOException
     */
    void doConfigure(String path, LoggerRepository repository) throws IOException;
    void doConfigure(T t, LoggerRepository repository);
}
