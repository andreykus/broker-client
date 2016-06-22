package com.bivgroup.broker.mq.impl.kafka.consumer;

import com.bivgroup.broker.mq.interfaces.Consumer;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProvider;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 * Created by bush on 22.06.2016.
 */
public class KafkaConsumerFactory {
    @Produces
    @Default
    @MessageProvider
    Consumer createKafkaConsumer(KafkaConsumerNew consumer) {
        return consumer;
    }
}
