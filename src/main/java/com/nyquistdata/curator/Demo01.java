package com.nyquistdata.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/2
 * @description:
 */
public class Demo01 {
    public static void main(String[] args) throws Exception {
        String zkAddress = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
        client.start();

        String path = client.create()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/user", "demo01".getBytes());
        System.out.println(path);

        Stat stat = client.checkExists().forPath("/user");

        System.out.println(stat != null);

        byte[] data = client.getData().forPath("/user");
        System.out.println(new String(data));

        stat = client.setData().forPath("/user", "data".getBytes());
        data = client.getData().forPath("/user");

        System.out.println(new String(data));

        for (int i = 0; i < 3; i++) {
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath("/user/child-");
        }

        List<String> children = client.getChildren().forPath("/user");

        children.forEach(s -> System.out.println(s));

        client.delete().deletingChildrenIfNeeded().forPath("/user");

    }
}
