package com.bivgroup.broker.mq.impl.kafka.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.bivgroup.broker.mq.impl.kafka.constant.KafkaConstants.PARTITIONER_CLASS;

/**
 * Created by bush on 20.06.2016.
 */
public class KafkaProducerNew {

    private class DemoProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    void o() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();

        //1
        KafkaProducer<Integer, String> producer1 = new KafkaProducer<Integer,
                String>(properties);
        ProducerRecord<Integer, String> record = new ProducerRecord<Integer,
                String>("mytesttopic", "mess");
        producer1.send(record);

        RecordMetadata rez = producer1.send(record).get();

        producer1.send(record, new DemoProducerCallback());

        producer1.close();

        //2
        Producer<Integer, String> producer = new Producer<>(new ProducerConfig(properties));
        KeyedMessage<Integer, String> data = new KeyedMessage<>("mytesttopic", "mess");
        producer.send(data);
        producer.close();

        //3
        properties.setProperty(PARTITIONER_CLASS, "com.bivgroup.broker.mq.impl.kafka");
        producer1 = new KafkaProducer<Integer,
                String>(properties);
        for (int iCount = 0; iCount < 100; iCount++) {
            int partition = iCount %
                    producer1.partitionsFor("mytesttopic").size();
            String message = "My Test Message No " + iCount;
            ProducerRecord<Integer, String> record1 = new
                    ProducerRecord<Integer, String>("mytesttopic",
                    partition, iCount, message);
            producer1.send(record1);
        }
        //4


    }
}
