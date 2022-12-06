package com.nyquistdata.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/6
 * @description:
 */
public class CglibProxy implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("pre-executed .....");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("post-executed....");
        return result;
    }

    public Object getProxy(Class clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }
}
