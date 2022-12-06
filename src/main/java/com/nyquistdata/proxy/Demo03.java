package com.nyquistdata.proxy;

import javassist.*;

import java.lang.reflect.Method;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/6
 * @description:
 */
public class Demo03 {
    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault();

        CtClass clazz = cp.makeClass("com.nyquistdata.proxy.JavassistDemo2");

        StringBuffer body = null;

        CtField field = new CtField(cp.get("java.lang.String"), "prop", clazz);

        field.setModifiers(Modifier.PRIVATE);

        clazz.addMethod(CtNewMethod.setter("getProp", field));
        clazz.addMethod(CtNewMethod.getter("setProp", field));

        clazz.addField(field, CtField.Initializer.constant("MyName"));

        CtConstructor constructor = new CtConstructor(
                new CtClass[]{}, clazz);

        body = new StringBuffer();

        body.append("{\n prop=\"MyName\";\n}");

        constructor.setBody(body.toString());
        clazz.addConstructor(constructor);

        CtMethod executeMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, clazz);
        executeMethod.setModifiers(Modifier.PUBLIC);

        body = new StringBuffer();

        body.append("{\n System.out.println(\"execute():\"" +
                "+this.prop);");
        body.append("\n}");

        executeMethod.setBody(body.toString());
        clazz.addMethod(executeMethod);

        clazz.writeFile();

        Class<?> c = clazz.toClass();

        Object o = c.newInstance();

        Method method = o.getClass().getMethod("execute", new Class[]{});

        method.invoke(o, new Object[]{});

    }
}
