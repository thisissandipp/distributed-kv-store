package com.example.distributed_kv_store.cluster;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("cluster")
public class ClusterProperties {
    private int selfId;
    private List<ClusterNode> nodes;

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public int getSelfId() {
        return this.selfId;
    }

    public void setNodes(List<ClusterNode> nodes) {
        this.nodes = nodes;
    }

    public List<ClusterNode> getNodes() {
        return this.nodes;
    }
}
