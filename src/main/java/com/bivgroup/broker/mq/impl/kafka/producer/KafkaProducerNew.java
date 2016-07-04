package com.bivgroup.broker.mq.impl.kafka.producer;

import com.bivgroup.broker.exceptions.MessageException;
import com.bivgroup.broker.mq.interfaces.Message;
import com.bivgroup.broker.mq.interfaces.annotations.MessageConfigProvider;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProviderType;
import com.bivgroup.config.Config;
import com.bivgroup.config.annotations.BundleProvider;
import com.bivgroup.config.annotations.LoggerProvider;
import com.bivgroup.config.annotations.types.LoggerType;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

/**
 * Created by bush on 20.06.2016.
 * Отправка сообщения KAFKA
 */

public class KafkaProducerNew implements com.bivgroup.broker.mq.interfaces.Producer<Message> {

    @Inject
    @MessageConfigProvider(type = MessageProviderType.KAFKA)
    public Config config;
    public KafkaProducer<Integer, String> producer;
    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;
    @Inject
    @BundleProvider
    private ResourceBundle bundle;

    private void init() {
        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer,
                String>(config.getProperties());
        this.producer = producer;

        onStart();

    }

    private void printMetricOnStart() {
        Map<MetricName, ? extends Metric> metrics = producer.metrics();
        StringBuilder sb = new StringBuilder();
        // add kafka producer stats, which are rates
        for (Map.Entry<MetricName, ? extends Metric> e : metrics.entrySet()) {
            sb.append("kafka.metric").append(e.getKey()).append(": ").append(e.getValue().value()).append('\n');
        }
        logger.debug(String.format(bundle.getString("message.kafka.metric.on.start"), sb));
    }

    private void onStart() {
        printMetricOnStart();
    }


    private void sendMessage(Message messageIn) throws ExecutionException, InterruptedException, MessageException {

        String topic = "mytesttopic1";

        init();


        //1

        ProducerRecord<Integer, String> record = new ProducerRecord<Integer,
                String>(topic, messageIn.getPayload().toString());

        logger.debug(String.format(bundle.getString("message.kafka.send.message="), messageIn.getPayload().toString()));
        RecordMetadata rez = this.producer.send(record).get();

        //        RecordMetadata rez = this.producer.send(record).get();
        //
        //        logger.info(
        //                rez.partition() + ":"
        //                        + rez.offset() + ":"
        //                        + rez.topic());
        //
        //        this.producer.send(record, new DemoProducerCallback());

        close();


        //        message = "mess 2";
        //        //2
        //        Producer<Integer, String> producer1 = new Producer<>(new ProducerConfig(config.getProperties()));
        //
        //
        //        KeyedMessage<Integer, String> data = new KeyedMessage<>(topic, message);
        //        // producer1.send(data);
        //        producer1.close();
        //
        //        //3
        //        Properties properties = config.getProperties();
        //        properties.setProperty(PARTITIONER_CLASS, "com.bivgroup.broker.mq.impl.kafka.message.SimplePartitioner");
        //        this.producer = new KafkaProducer<Integer,
        //                String>(properties);
        //        for (int iCount = 0; iCount < 100; iCount++) {
        //            int partition = iCount %
        //                    this.producer.partitionsFor(topic).size();
        //            message = "My Test Message No " + iCount;
        //            ProducerRecord<Integer, String> record1 = new
        //                    ProducerRecord<Integer, String>(topic,
        //                    partition, iCount, message);
        //            this.producer.send(record1);
        //        }


    }

    @Override
    public void send(Message message) throws MessageException {
        try {
            sendMessage(message);
        } catch (ExecutionException e) {
            throw new MessageException(e);
        } catch (InterruptedException e) {
            throw new MessageException(e);
        }
    }

    @Override
    public void close() throws MessageException {
        this.producer.close();
    }

    @Override
    public String getProducerKey() throws MessageException {
        return null;
    }
}
