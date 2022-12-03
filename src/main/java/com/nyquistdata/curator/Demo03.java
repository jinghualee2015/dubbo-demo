package com.nyquistdata.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/2
 * @description:
 */
public class Demo03 {
    public static void main(String[] args) throws Exception {
        String zkAddress = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);

        client.start();

        client.getConnectionStateListenable().addListener(
                new ConnectionStateListener() {
                    @Override
                    public void stateChanged(CuratorFramework client,
                                             ConnectionState newState) {
                        switch (newState) {
                            case CONNECTED:
                                System.out.println("Connected to Server....");
                                break;
                            case SUSPENDED:
                                System.out.println("Zookeeper 的连接丢失");
                                break;
                            case RECONNECTED:
                                System.out.println("丢失连接被重新建立");
                                break;
                            case LOST:
                                System.out.println("丢失连接");
                                break;
                            case READ_ONLY:
                                System.out.println("链接进入只读模式");
                                break;
                            default:

                        }

                    }
                }
        );

        System.in.read();

    }
}
