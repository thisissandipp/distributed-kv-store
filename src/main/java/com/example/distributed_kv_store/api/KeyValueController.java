package com.example.distributed_kv_store.api;

import com.example.distributed_kv_store.api.dto.KeyValueResponse;
import com.example.distributed_kv_store.cluster.ClusterManager;
import com.example.distributed_kv_store.cluster.ClusterNode;
import com.example.distributed_kv_store.log.LogEntry;
import com.example.distributed_kv_store.log.ReplicationClient;
import com.example.distributed_kv_store.log.ReplicationLog;
import com.example.distributed_kv_store.service.KeyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kv")
public class KeyValueController {
    private final KeyValueService keyValueService;
    private final ReplicationLog replicationLog;
    private final ClusterManager clusterManager;

    private final ReplicationClient replicationClient;

    @Autowired
    public KeyValueController(KeyValueService keyValueService, ReplicationLog replicationLog, ClusterManager clusterManager, ReplicationClient replicationClient) {
        this.keyValueService = keyValueService;
        this.replicationLog = replicationLog;
        this.clusterManager = clusterManager;
        this.replicationClient = replicationClient;
    }

    @PostMapping("/put")
    public ResponseEntity<Void> putValue(@RequestParam String key, @RequestParam String value) {
        // Populate the log entry
        LogEntry logEntry = replicationLog.append(key, value);

        // Send replicate requests to the replicas
        List<ClusterNode> clusterNodes = clusterManager.getClusterNodes();
        int replicateAck = replicationClient.replicateToFollowers(logEntry);

        if (replicateAck < (clusterNodes.size() - 1) / 2) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        int commitAck = replicationClient.commitToFollowers(logEntry.getIndex());
        if (commitAck < (clusterNodes.size() - 1) / 2) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        this.keyValueService.put(key, value);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<KeyValueResponse> getValue(@RequestParam String key) {
        String value = this.keyValueService.get(key);

        if (value == null) {
            String message = "Key: " + key + " is not found.";
            KeyValueResponse errorResponse = new KeyValueResponse(key, message, true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        KeyValueResponse response = new KeyValueResponse(key, value);
        return ResponseEntity.ok(response);
    }
}
