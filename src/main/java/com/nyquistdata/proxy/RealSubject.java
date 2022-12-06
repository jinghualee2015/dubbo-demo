package com.nyquistdata.proxy;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/6
 * @description:
 */
public class RealSubject implements Subject {
    @Override
    public void operation() {
        System.out.println("RealSubject operate....");
    }
}
