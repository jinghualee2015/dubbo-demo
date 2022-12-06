package com.nyquistdata.proxy;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/6
 * @description:
 */
public class Demo02 {

    public String method(String str) {
        System.out.println(str);

        return "CGLib test method: " + str;
    }

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();

        Demo02 demo2 = (Demo02) proxy.getProxy(Demo02.class);

        String result = demo2.method("test");

        System.out.println(result);


    }
}
