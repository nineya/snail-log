package com.nineya.slog;

/**
 * @author linsongwang
 * @date 2020/6/21 16:12
 */
public class AutoMain {
    private static Logger logger = Logger.getLogger("sys");
    public static void main(String[] args) {
        logger.fatal("hello slog as fatal.");
        logger.error("hello slog as error.");
        logger.warn("hello slog as warn.");
        logger.info("hello slog as info.");
        logger.debug("hello slog as debug.");
        logger.trace("hello slog as debug.");

        logger.infoTitle(1, "know slog");

        logger.error("hello slog as error.", new Throwable());
        String message = "hello {}.";
        logger.info(message, "slog");
    }
}
