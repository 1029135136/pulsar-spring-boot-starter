package com.ang.swarm.pulsar;

import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: huangyibo
 * @Date: 2022/5/28 2:35
 * @Description: Pulsar的核心服务类
 */

public class PulsarCommon {


    @Autowired
    private PulsarClient client;


    /**
     * 创建一个生产者
     *
     * @param topic  topic name
     * @param schema schema方式
     * @param <T>    泛型
     * @return Producer生产者
     */
    public <T> Producer<T> createProducer(String topic, Schema<T> schema) {

        try {
            return client.newProducer(schema)
                         .compressionType(CompressionType.LZ4)
                         .topic(topic)
                         .create();
        } catch (PulsarClientException e) {
            throw new RuntimeException("初始化Pulsar Producer失败");
        }
    }


    /**
     * @param topic           topic name
     * @param subscription    sub name
     * @param messageListener MessageListener的自定义实现类
     * @param schema          schema消费方式
     * @param <T>             泛型
     * @return Consumer消费者
     */
    public <T> Consumer<T> createConsumer(String topic, String subscription,
                                          MessageListener<T> messageListener, Schema<T> schema) {
        try {
            return client.newConsumer(schema)
                         .topic(topic)
                         .subscriptionName(subscription)
                         .subscriptionType(SubscriptionType.Shared)
                         .enableRetry(true)
                         .messageListener(messageListener)
                         .subscribe();
        } catch (PulsarClientException e) {
            throw new RuntimeException("初始化Pulsar Consumer失败");
        }
    }


    /**
     * 异步发送一条消息
     *
     * @param message  消息体
     * @param producer 生产者实例
     * @param <T>      消息泛型
     */
    public <T> void sendAsyncMessage(T message, Producer<T> producer) {
        producer.sendAsync(message)
                .thenAccept(msgId -> {
                });
    }


    /**
     * 同步发送一条消息
     *
     * @param message  消息体
     * @param producer 生产者实例
     * @param <T>      泛型
     * @throws PulsarClientException
     */
    public <T> void sendSyncMessage(T message, Producer<T> producer) throws PulsarClientException {
        MessageId send = producer.send(message);
    }
}
