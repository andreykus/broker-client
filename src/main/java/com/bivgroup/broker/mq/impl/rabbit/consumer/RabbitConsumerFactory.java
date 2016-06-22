package com.bivgroup.broker.mq.impl.rabbit.consumer;

import com.bivgroup.broker.mq.interfaces.Consumer;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProvider;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

/**
 * Created by bush on 22.06.2016.
 */
public class RabbitConsumerFactory {
    @Produces
    @Alternative
    @MessageProvider
    Consumer createRabbitConsumer(RabbitConsumerNew consumer) {
        return consumer;
    }
}
