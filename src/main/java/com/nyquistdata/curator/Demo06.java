package com.nyquistdata.curator;

import java.util.List;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/3
 * @description:
 */
public class Demo06 {
    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.setHost("127.0.0.1");
        config.setPort(2181);
        config.setPath("/demo-service");

        ZooKeeperCoordinator coordinator = new ZooKeeperCoordinator(config);

        Thread registerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int startPort = 8088;
                String serviceName = "demo-provider-";
                int index = 1;
                while (true) {
                    try {
                        ServiceInfo info = new ServiceInfo();
                        info.setAddress("127.0.0.1");
                        info.setPort(startPort++);
                        info.setName(serviceName + (++index));
                        coordinator.registerRemote(info);
                        Thread.sleep(1000);
                    } catch (Exception ex) {

                    }
                }
            }
        }, "register");
        registerThread.start();

        Thread consume = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<ServiceInfo> serviceInfoList = coordinator.queryRemoteNodes();
                    System.out.println("=================begin=====================");
                    serviceInfoList.forEach(serviceInfo -> System.out.println(serviceInfo.toString()));
                    System.out.println("=================end=====================");
                }
            }
        });

        consume.start();

        System.in.read();

    }
}
