package com.example.distributed_kv_store.api;

import com.example.distributed_kv_store.api.dto.ClusterNodeResponse;
import com.example.distributed_kv_store.cluster.ClusterManager;
import com.example.distributed_kv_store.cluster.ClusterNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cluster")
public class ClusterNodesController {
    private final ClusterManager clusterManager;

    @Autowired
    public ClusterNodesController(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    @GetMapping("/nodes")
    public ResponseEntity<ClusterNodeResponse> getClusterNodes() {
        List<ClusterNode> clusterNodes = this.clusterManager.getClusterNodes();
        ClusterNode self = this.clusterManager.getSelfNode();

        ClusterNodeResponse response = new ClusterNodeResponse(self, clusterNodes);
        return ResponseEntity.ok(response);
    }
}
