package com.nyquistdata.proxy;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/6
 * @description:
 */
public class Demo01 {
    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Subject target = new RealSubject();
        DemoInvokeHandler handler = new DemoInvokeHandler(target);

        Subject proxy = (Subject) handler.getProxy();

        proxy.operation();
    }
}
