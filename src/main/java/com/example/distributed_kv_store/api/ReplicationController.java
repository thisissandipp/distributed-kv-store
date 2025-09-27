package com.example.distributed_kv_store.api;

import com.example.distributed_kv_store.log.LogEntry;
import com.example.distributed_kv_store.log.ReplicationLog;
import com.example.distributed_kv_store.service.KeyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class ReplicationController {
    private final ReplicationLog replicationLog;
    private final KeyValueService keyValueService;

    @Autowired
    public ReplicationController(ReplicationLog replicationLog, KeyValueService keyValueService) {
        this.replicationLog = replicationLog;
        this.keyValueService = keyValueService;
    }

    @PostMapping("/replicate")
    public ResponseEntity<String> replicate(@RequestBody LogEntry logEntry) {
        LogEntry entry = replicationLog.appendAtIndex(logEntry);
        if (entry == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("ACK: " + logEntry.getIndex());
    }

    @PostMapping("/commit/{index}")
    public ResponseEntity<String> commit(@PathVariable int index) {
        LogEntry logEntry = replicationLog.get(index);
        if (logEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean success = replicationLog.markLogCommitted(index);
        if (!success) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        keyValueService.put(logEntry.getKey(), logEntry.getValue());
        return ResponseEntity.ok("ACK: " + index);
    }
}
