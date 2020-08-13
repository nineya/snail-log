package com.nineya.slog;

import org.junit.Test;

/**
 * @author linsongwang
 * @date 2020/8/13
 */
public class LoggerTest {
    private Logger logger = Logger.getLogger(LoggerTest.class);
    @Test
    public void testInfo(){
        logger.fatal("hello slog as fatal.");
        logger.error("hello slog as error.");
        logger.warn("hello slog as warn.");
        logger.info("hello slog as info.");
        logger.debug("hello slog as debug.");
        logger.trace("hello slog as debug.");

        logger.error("hello slog as error.", new Throwable());
        String message = "hello {}.";
        logger.info(message, "slog");
    }
}
