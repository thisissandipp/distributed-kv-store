package com.example.distributed_kv_store.cluster;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("cluster")
public class ClusterProperties {
    private int selfId;
    private List<ClusterNode> nodes;
}
