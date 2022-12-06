package com.nyquistdata.proxy;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/6
 * @description:
 */
public class Demo04 {
    public static void main(String[] args) throws Exception {
        ProxyFactory factory = new ProxyFactory();

        factory.setSuperclass(JavassistDemo.class);

        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method method) {
                if (method.getName().equals("execute")) {
                    return true;
                }
                return false;
            }
        });

        factory.setHandler(new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                System.out.println("前置处理");
                Object result = proceed.invoke(self, args);
                System.out.println("执行结果:" + result);
                System.out.println("后置处理");
                return result;
            }
        });


        Class<?> c = factory.createClass();

        JavassistDemo demo = (JavassistDemo) c.newInstance();
        demo.execute();

        System.out.println(demo.getProp());

    }

}
