package com.example.distributed_kv_store.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KeyValueService {
    private final Map<String, String> _store = new ConcurrentHashMap<>();

    public void put(String key, String value) {
        _store.put(key, value);
    }

    public String get(String key) {
        if (!_store.containsKey(key)) return null;
        return _store.get(key);
    }
}
