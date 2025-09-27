package com.example.distributed_kv_store.log;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ReplicationLog {
    private final List<LogEntry> logEntries = new ArrayList<>();
    private final AtomicInteger nextIndex = new AtomicInteger(0);

    public synchronized LogEntry append(String key, String value) {
        int index = nextIndex.getAndIncrement();
        LogEntry logEntry = new LogEntry(index, key, value, false, Instant.now());
        logEntries.add(logEntry);
        return logEntry;
    }

    public synchronized LogEntry appendAtIndex(LogEntry logEntry) {
        int index = logEntry.getIndex();
        if (index < logEntries.size()) {
            LogEntry existingEntry = logEntries.get(index);
            if (existingEntry.getKey().equals(logEntry.getKey()) && existingEntry.getValue().equals(logEntry.getValue())) {
                return logEntry;
            } else {
                return null;
            }
        }
        if (index == logEntries.size()) {
            logEntries.add(logEntry);
            return logEntry;
        }
        return null;
    }

    public synchronized boolean markLogCommitted(int index) {
        Optional<LogEntry> logEntry = logEntries.stream().filter(e -> e.getIndex() == index).findFirst();
        if (logEntry.isPresent()) {
            logEntry.get().setCommitted(true);
            return true;
        }
        return false;
    }

    public synchronized LogEntry get(int index) {
        if (index < 0 || index >= logEntries.size()) {
            return null;
        }
        return logEntries.get(index);
    }
}
