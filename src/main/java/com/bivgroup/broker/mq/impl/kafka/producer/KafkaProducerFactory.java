package com.bivgroup.broker.mq.impl.kafka.producer;

import com.bivgroup.broker.mq.interfaces.Producer;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProvider;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 * Created by bush on 20.06.2016.
 */


public class KafkaProducerFactory {
    @Produces
    @Default
    @MessageProvider
    Producer createKafkaProducer(KafkaProducerNew producer) {
        return producer;
    }


}
