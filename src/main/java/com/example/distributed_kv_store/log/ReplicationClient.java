package com.example.distributed_kv_store.log;

import com.example.distributed_kv_store.cluster.ClusterManager;
import com.example.distributed_kv_store.cluster.ClusterNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReplicationClient {
    private final ClusterManager clusterManager;
    private final RestTemplate restTemplate;

    public ReplicationClient(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
        this.restTemplate = new RestTemplate();
    }

    public int replicateToFollowers(LogEntry logEntry) {
        int ack = 0;
        List<ClusterNode> clusterNodes = clusterManager.getClusterNodes();
        ClusterNode self = clusterManager.getSelfNode();

        for (ClusterNode clusterNode : clusterNodes) {
            if (clusterNode != self) {
                try {
                    restTemplate.postForEntity(clusterNode.getNodeURL() + "/internal/replicate", logEntry, String.class);
                    ack++;
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        return ack;
    }

    public int commitToFollowers(int index) {
        int ack = 0;
        List<ClusterNode> clusterNodes = clusterManager.getClusterNodes();
        ClusterNode self = clusterManager.getSelfNode();

        for (ClusterNode clusterNode : clusterNodes) {
            if (clusterNode != self) {
                try {
                    restTemplate.postForEntity(clusterNode.getNodeURL() + "/internal/commit/" + index, null, String.class);
                    ack++;
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        return ack;
    }
}
