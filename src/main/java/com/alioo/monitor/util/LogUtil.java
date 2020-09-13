package com.alioo.monitor.util;

import org.slf4j.Logger;

public class LogUtil {



    public static void info(Logger logger,String message,String ... params){
        logger.info(message,params);
    }
}
