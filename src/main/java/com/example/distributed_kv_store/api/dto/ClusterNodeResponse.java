package com.example.distributed_kv_store.api.dto;

import com.example.distributed_kv_store.cluster.ClusterNode;
import lombok.Getter;

import java.util.List;

@Getter
public class ClusterNodeResponse {
    private final ClusterNode self;
    private final List<ClusterNode> nodes;

    public ClusterNodeResponse(ClusterNode self, List<ClusterNode> nodes) {
        this.self = self;
        this.nodes = nodes;
    }

}
