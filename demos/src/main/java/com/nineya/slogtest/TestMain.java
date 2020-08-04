package com.nineya.slogtest;

import com.nineya.snaillog.Logger;

/**
 * @author linsongwang
 * @date 2020/6/21 16:12
 */
public class TestMain {
    private static Logger logger = Logger.getLogger("sys");
    private static Logger logger2 = Logger.getLogger(TestMain.class);
    public static void main(String[] args) {
        logger.info("傻叉");
    }
}
