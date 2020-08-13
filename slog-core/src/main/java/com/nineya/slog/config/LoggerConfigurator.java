package com.nineya.slog.config;

import com.nineya.slog.internal.LoggerConfiguration;
import com.nineya.slog.LoggerRepository;

/**
 * @author linsongwang
 * @date 2020/7/4 18:10
 */
public class LoggerConfigurator implements Configurator<LoggerConfiguration> {

    @Override
    public void doConfigure(String path, LoggerRepository repository) {

    }

    @Override
    public void doConfigure(LoggerConfiguration loggerConfiguration, LoggerRepository repository) {

    }
}
