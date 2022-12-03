package com.nyquistdata.log;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/11/27
 * @description:
 */
public class App {
    public static void main(String[] args) {
        ServiceLoader<Log> serviceLoader = ServiceLoader.load(Log.class);
        Iterator<Log> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Log log = iterator.next();
            log.log("JDK SPI.");
        }
    }
}
