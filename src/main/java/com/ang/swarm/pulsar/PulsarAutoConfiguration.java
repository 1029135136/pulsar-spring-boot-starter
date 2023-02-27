package com.ang.swarm.pulsar;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 于昂
 * @date: 2023/2/23
 **/
@Configuration
@Slf4j
@EnableConfigurationProperties(PulsarProperties.class)
@ConditionalOnMissingBean(PulsarClient.class)
public class PulsarAutoConfiguration {
    @Autowired
    private PulsarProperties pulsarProperties;

    @Bean
    @ConditionalOnMissingBean(PulsarClient.class)
    public PulsarClient pulsarClient() {
        try {
            return PulsarClient.builder()
                               .serviceUrl(pulsarProperties.getServiceurl())
                               .build();
        } catch (PulsarClientException e) {
            log.error("PulsarClient init error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PulsarCommon pulsarCommon() {
        return new PulsarCommon();
    }
}
