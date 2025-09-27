package com.example.distributed_kv_store.cluster;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ClusterNode implements Serializable {
    private int id;
    private String host;
    private int port;
    private boolean isLeader;

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

    public String getNodeURL() {
        return "http://" + host + ":" + port;
    }
}
