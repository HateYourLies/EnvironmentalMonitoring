package com.briup.logger;

import org.apache.log4j.Logger;
/**
 * 1.依赖  2.配置文件 3.使用
 */
public class TestLogger {
    public static void main(String[] args) {
        // 获取日志对象 不指定类
//        Logger rootLogger = Logger.getRootLogger();
        Logger logger = Logger.getLogger(TestLogger.class);
        // 以下级别是从低到高 如果degug以及以上都会输出
            // info
        logger.debug("debug日志");
        logger.info("info日志");
        logger.warn("warin日志");
        logger.error("error日志");
        logger.fatal("fatal日志");
    }
}
