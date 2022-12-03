package com.nyquistdata.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/2
 * @description:
 */
public class Demo02 {
    public static void main(String[] args) throws Exception {
        String zkAddress = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);

        client.start();

        client.getCuratorListenable().addListener(
                new CuratorListener() {
                    @Override
                    public void eventReceived(CuratorFramework client,
                                              CuratorEvent event) throws Exception {
                        switch (event.getType()) {
                            case CREATE:
                                System.out.println("CREATE: " + event.getPath());
                                break;
                            case DELETE:
                                System.out.println("DELETE: " + event.getPath());
                                break;
                            case EXISTS:
                                System.out.println("EXISTS: " + event.getPath());
                                break;
                            case GET_DATA:
                                System.out.println("GET_DATA: " + event.getPath() + ", "
                                        + new String(event.getData()));
                                break;
                            case SET_DATA:
                                System.out.println("SET_DATA: " + event.getPath() + ", "
                                        + new String(event.getData()));
                                break;
                            case CHILDREN:
                                System.out.println("CHILDREN: " + event.getPath());
                                break;
                            default:
                        }
                    }
                }
        );

        client.create().withMode(CreateMode.PERSISTENT)
                .inBackground()
                .forPath("/user", "demo02".getBytes());
        client.checkExists().inBackground()
                .forPath("/user");
        client.setData().inBackground()
                .forPath("/user", "setData-Test".getBytes());

        client.getData().inBackground()
                .forPath("/user");

        for (int i = 0; i < 3; i++) {
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .inBackground()
                    .forPath("/user/child-");
        }

        client.getChildren().inBackground().forPath("/user");

        client.getChildren().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("in background:"

                        + event.getType() + "," + event.getPath());
            }
        }).forPath("/user");

        client.delete().deletingChildrenIfNeeded()
                .inBackground()
                .forPath("/user");
        System.in.read();
    }
}
