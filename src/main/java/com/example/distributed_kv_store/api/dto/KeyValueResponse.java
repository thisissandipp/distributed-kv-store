package com.example.distributed_kv_store.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
