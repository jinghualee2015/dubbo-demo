package com.nyquistdata.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/2
 * @description:
 */
public class Demo05 {
    public static void main(String[] args) throws Exception {
        String zkAddress = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);

        client.start();

        NodeCache nodeCache = new NodeCache(client, "/user");
        nodeCache.start(true);

        if (nodeCache.getCurrentData() != null) {
            System.out.println("NodeCache 节点初始化数据为:" +
                    new String(nodeCache.getCurrentData().getData()));
        } else {
            System.out.println("NodeCache 节点数据为空");
        }

        nodeCache.getListenable().addListener(() -> {
            String data = new String(nodeCache.getCurrentData().getData());
            System.out.println("NodeCache 节点路径:" + nodeCache.getCurrentData().getPath() + ", 节点数据为:" + data);
        });

        PathChildrenCache childrenCache = new PathChildrenCache(client, "/user", true);

        childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        List<ChildData> children = childrenCache.getCurrentData();

        children.forEach(childData -> {
            System.out.println(new String(childData.getData()));
        });

        childrenCache.getListenable().addListener((client1, event) -> {
            System.out.println(LocalDateTime.now() + "  " + event.getType());
            switch (event.getType()) {
                case INITIALIZED:
                    System.out.println("PathChildrenCache:子节点初始化成功...");
                    break;
                case CHILD_ADDED:
                    System.out.println("PathChildrenCache添加子节点:" + event.getData().getPath());
                    System.out.println("PathChildrenCache子节点数据:" + new String(event.getData().getData()));
                    break;
                case CHILD_REMOVED:
                    System.out.println("PathChildrenCache删除子节点:" + event.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    System.out.println("PathChildrenCache修改子节点路径:" + event.getData().getPath());
                    System.out.println("PathChildrenCache修改子节点数据:" + new String(event.getData().getData()));
            }
        });


        TreeCache cache = TreeCache.newBuilder(client, "/user").setCacheData(false).build();

        cache.getListenable().addListener((c, event) -> {
            if (event.getData() != null) {
                System.out.println("TreeCache, type=" + event.getType() + " path=" + event.getData().getPath());
            } else {
                System.out.println("TreeCache,type=" + event.getType());
            }
        });
        cache.start();

        System.in.read();
    }
}
