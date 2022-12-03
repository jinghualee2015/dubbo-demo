package com.nyquistdata.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;

import java.util.List;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/2
 * @description:
 */
public class Demo04 {
    public static void main(String[] args) throws Exception {
        String zkAddress = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);

        client.start();

        try {
            client.create().withMode(CreateMode.PERSISTENT)
                    .forPath("/user", "test".getBytes());
        } catch (Exception Ex) {

        }

        List<String> children = client.getChildren().usingWatcher(
                new CuratorWatcher() {
                    @Override
                    public void process(WatchedEvent event) throws Exception {
                        System.out.println(event.getType() + ", " + event.getPath());
                    }
                }
        ).forPath("/user");

        System.out.println(children);
        System.in.read();
    }
}
