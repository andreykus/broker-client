package com.bivgroup.broker.mq.impl.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by bush on 20.06.2016.
 */
public class KafkaProducerNew {

    void o(){
        Properties properties = new Properties();
        Producer<Integer, String> producer = new Producer<>(new ProducerConfig(properties));
        KafkaProducerNew<Integer, String> producer1 = new KafkaProducerNew<Integer,
                        String>(properties);

        KeyedMessage<Integer, String> data = new KeyedMessage<>("", "");
        producer.send(data);
        producer.close();





    }
}
