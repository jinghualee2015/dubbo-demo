package com.nyquistdata.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/3
 * @description:
 */
public class ZooKeeperCoordinator {

    private ServiceDiscovery<ServiceInfo> serviceDiscovery;

    private ServiceCache<ServiceInfo> serviceCache;

    private CuratorFramework client;

    private String root;

    private InstanceSerializer serializer = new JsonInstanceSerializer(ServiceInfo.class);

    ZooKeeperCoordinator(Config config) throws Exception {
        this.root = config.getPath();

        client = CuratorFrameworkFactory.newClient(
                config.getHostPort(),
                new ExponentialBackoffRetry(3, 1000)
        );

        client.start();

        client.blockUntilConnected();

        serviceDiscovery = ServiceDiscoveryBuilder
                .builder(ServiceInfo.class)
                .client(client)
                .basePath(root)
                .watchInstances(true)
                .serializer(serializer)
                .build();

        serviceDiscovery.start();

        serviceCache = serviceDiscovery.serviceCacheBuilder()
                .name(root)
                .build();
        serviceCache.start();


    }

    public void registerRemote(ServiceInfo serviceInfo) throws Exception {
        ServiceInstance<ServiceInfo> thisInstance =
                ServiceInstance.<ServiceInfo>builder()
                        .name(serviceInfo.getName())
                        .id(UUID.randomUUID().toString())
                        .address(serviceInfo.getAddress())
                        .port(serviceInfo.getPort())
                        .payload(serviceInfo)
                        .build();
        serviceDiscovery.registerService(thisInstance);

    }

    public List<ServiceInfo> queryRemoteNodes() {
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        List<ServiceInstance<ServiceInfo>> serviceInstances =
                serviceCache.getInstances();
        serviceInstances.forEach(serviceInstance -> {
            ServiceInfo serviceInfo = serviceInstance.getPayload();
            serviceInfoList.add(serviceInfo);
        });
        return serviceInfoList;
    }
}
