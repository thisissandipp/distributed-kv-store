package com.example.distributed_kv_store.api.dto;

import com.example.distributed_kv_store.cluster.ClusterNode;

import java.util.List;

public class ClusterNodeResponse {
    private final ClusterNode self;
    private final List<ClusterNode> nodes;

    public ClusterNodeResponse(ClusterNode self, List<ClusterNode> nodes) {
        this.self = self;
        this.nodes = nodes;
    }

    public ClusterNode getSelf() {
        return self;
    }

    public List<ClusterNode> getNodes() {
        return nodes;
    }
}
