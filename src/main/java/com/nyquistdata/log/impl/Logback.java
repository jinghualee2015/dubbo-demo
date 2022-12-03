package com.nyquistdata.log.impl;

import com.nyquistdata.log.Log;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/11/27
 * @description:
 */
public class Logback implements Log {
    @Override
    public void log(String info) {
        System.out.println("Logback: " + info);
    }
}
