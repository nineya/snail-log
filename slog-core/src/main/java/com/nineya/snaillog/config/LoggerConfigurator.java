package com.nineya.snaillog.config;

import com.nineya.snaillog.internal.LoggerConfiguration;
import com.nineya.snaillog.LoggerRepository;

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
