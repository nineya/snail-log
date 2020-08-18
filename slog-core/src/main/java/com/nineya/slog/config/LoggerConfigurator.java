package com.nineya.slog.config;

import com.nineya.slog.internal.LoggerConfiguration;
import com.nineya.slog.LoggerRepository;

/**
 * @author linsongwang
 * @date 2020/7/4 18:10
 * 通过JavaApi的方式生成配置信息
 */
public class LoggerConfigurator implements Configurator<LoggerConfiguration> {

    /**
     * 因为是JavaApi的方式实现配置信息获取，不具有文件路径，该方法不做实现
     * @param path 配置文件路径
     * @param repository 一些公共配置信息存储的对象
     */
    @Override
    public void doConfigure(String path, LoggerRepository repository) {

    }

    /**
     * 使用LoggerConfiguration进行日志信息配置
     * @param loggerConfiguration 配置信息
     * @param repository 一些公共配置信息存储的对象
     */
    @Override
    public void doConfigure(LoggerConfiguration loggerConfiguration, LoggerRepository repository) {

    }
}
