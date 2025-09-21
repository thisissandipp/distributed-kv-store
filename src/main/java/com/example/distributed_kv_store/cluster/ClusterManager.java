package com.example.distributed_kv_store.cluster;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClusterManager {
    private final ClusterProperties clusterProperties;

    public ClusterManager(ClusterProperties clusterProperties) {
        this.clusterProperties = clusterProperties;
    }

    public ClusterNode getSelfNode() {
        return this.clusterProperties.getNodes().stream()
                .filter((clusterNode -> clusterNode.getId() == clusterProperties.getSelfId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Self Node was not found!"));
    }

    public List<ClusterNode> getClusterNodes() {
        return this.clusterProperties.getNodes();
    }

    public int getNodeSelfId() {
        return this.clusterProperties.getSelfId();
    }
}
