package com.bivgroup.broker.mq.impl.kafka.producer;

import com.bivgroup.broker.exceptions.MessageException;
import com.bivgroup.broker.mq.MessageConfigProvider;
import com.bivgroup.broker.mq.MessageConfigType;
import com.bivgroup.broker.mq.impl.kafka.producer.callback.DemoProducerCallback;
import com.bivgroup.broker.mq.interfaces.Message;
import com.bivgroup.config.Config;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import javax.inject.Inject;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.bivgroup.broker.mq.impl.kafka.constant.KafkaConstants.PARTITIONER_CLASS;

/**
 * Created by bush on 20.06.2016.
 */

public class KafkaProducerNew implements com.bivgroup.broker.mq.interfaces.Producer<Message> {

    @Inject
    @MessageConfigProvider(type = MessageConfigType.KAFKA)
    public Config config;


    public KafkaProducer<Integer, String> producer;

    public void init() {
        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer,
                String>(config.getProperties());
        this.producer = producer;
    }


    void o() throws ExecutionException, InterruptedException {


        //1
        ProducerRecord<Integer, String> record = new ProducerRecord<Integer,
                String>("mytesttopic", "mess");
        this.producer.send(record);

        RecordMetadata rez = this.producer.send(record).get();

        this.producer.send(record, new DemoProducerCallback());

        this.producer.close();

        //2
        Producer<Integer, String> producer = new Producer<>(new ProducerConfig(config.getProperties()));
        KeyedMessage<Integer, String> data = new KeyedMessage<>("mytesttopic", "mess");
        producer.send(data);
        producer.close();

        //3
        Properties properties = config.getProperties();
        properties.setProperty(PARTITIONER_CLASS, "com.bivgroup.broker.mq.impl.kafka");
        this.producer = new KafkaProducer<Integer,
                String>(properties);
        for (int iCount = 0; iCount < 100; iCount++) {
            int partition = iCount %
                    this.producer.partitionsFor("mytesttopic").size();
            String message = "My Test Message No " + iCount;
            ProducerRecord<Integer, String> record1 = new
                    ProducerRecord<Integer, String>("mytesttopic",
                    partition, iCount, message);
            this.producer.send(record1);
        }
        //4


    }

    @Override
    public void send(Message message) throws MessageException {

    }

    @Override
    public void close() throws MessageException {

    }

    @Override
    public String getProducerKey() throws MessageException {
        return null;
    }
}
