package com.example.distributed_kv_store.api;

import com.example.distributed_kv_store.api.dto.KeyValueResponse;
import com.example.distributed_kv_store.service.KeyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kv")
public class KeyValueController {
    private final KeyValueService keyValueService;

    @Autowired
    public KeyValueController(KeyValueService keyValueService) {
        this.keyValueService = keyValueService;
    }

    @PostMapping("/put")
    public ResponseEntity<Void> putValue(@RequestParam String key, @RequestParam String value) {
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
