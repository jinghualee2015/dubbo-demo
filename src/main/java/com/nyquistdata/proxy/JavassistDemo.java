package com.nyquistdata.proxy;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/6
 * @description:
 */
public class JavassistDemo {
    String prop = "MyName";

    public JavassistDemo(){
        prop = "MyName";
    }

    public void setProp(String paramString) {

        this.prop = paramString;

    }

    public String getProp() {

        return this.prop;

    }

    public void execute() {

        System.out.println("execute():" + this.prop);

    }
}
