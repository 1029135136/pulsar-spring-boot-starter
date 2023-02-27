package com.ang.swarm.pulsar;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: huangyibo
 * @Date: 2022/5/28 2:32
 * @Description: Pulsar 参数类
 */

@ConfigurationProperties(prefix = "pulsar")
@Data
public class PulsarProperties {

    /**
     * 接入地址
     */
    private String serviceurl;

    /**
     * 集群name
     */
    private String cluster;


    private Map<String, String> topicMap;


    /**
     * 订阅
     */
    private Map<String, String> subMap;
}
