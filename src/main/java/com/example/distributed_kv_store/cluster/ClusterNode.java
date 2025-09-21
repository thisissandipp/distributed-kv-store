package com.example.distributed_kv_store.cluster;

import java.io.Serializable;

public class ClusterNode implements Serializable {
    private int id;
    private String host;
    private int port;
    private boolean isLeader;

    public ClusterNode() {}

    public ClusterNode(int id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.isLeader = false;
    }

    public ClusterNode(int id, String host, int port, boolean isLeader) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.isLeader = isLeader;
    }

    public int getId() {
        return this.id;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public boolean getIsLeader() {
        return this.isLeader;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIsLeader(boolean isLeader) {
        this.isLeader = isLeader;
    }

    public String getNodeURL() {
        return "http://" + host + ":" + port;
    }
}
