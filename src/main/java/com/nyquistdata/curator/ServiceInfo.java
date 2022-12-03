package com.nyquistdata.curator;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/3
 * @description:
 */
public class ServiceInfo {
    private String name;

    private String id;

    private String address;

    private int port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "address=" + address + " port=" + port + " name=" + name + " id=" + id;
    }
}
