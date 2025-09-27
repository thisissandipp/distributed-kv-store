package com.example.distributed_kv_store.log;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
public class LogEntry implements Serializable {
    private int index;
    private String key;
    private String value;
    @Builder.Default
    private boolean committed = false;
    @Builder.Default
    private Instant timestamp = Instant.now();
}
