package com.nyquistdata.curator;

/**
 * @author: Nyquist Data Tech Team
 * @version: 1.0
 * @date: 2022/12/3
 * @description:
 */
public class Config {
    private String path;
    private String host;
    private int port;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostPort() {
        return host + ":" + port;
    }
}
