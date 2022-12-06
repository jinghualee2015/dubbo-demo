package com.nyquistdata.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/6
 * @description:
 */
public class DemoInvokeHandler implements InvocationHandler {

    private Object target;

    DemoInvokeHandler(Subject target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy pre executed.....");
        Object result = method.invoke(target, args);
        System.out.printf("proxy post executed....");
        return result;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }


}
