package com.bivgroup.broker.mq.impl.rabbit.producer;

import com.bivgroup.broker.mq.interfaces.Producer;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProvider;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

/**
 * Created by bush on 22.06.2016.
 */


public class RabbitProducerFactory {
    @Produces
    @Alternative
    @MessageProvider
    Producer createKafkaProducer(RabbitProducerNew producer) {
        return producer;
    }

}
