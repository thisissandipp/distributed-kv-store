package com.example.distributed_kv_store.api.dto;

public class KeyValueResponse {
    private final String key;
    private final String value;
    private final String message;

    public KeyValueResponse(String key, String value) {
        this.key = key;
        this.value = value;
        this.message = null;
    }

    public KeyValueResponse(String key, String message, boolean isError) {
        this.key = key;
        this.value = null;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
