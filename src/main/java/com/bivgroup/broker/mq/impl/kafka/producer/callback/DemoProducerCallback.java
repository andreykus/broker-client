package com.bivgroup.broker.mq.impl.kafka.producer.callback;

import com.bivgroup.config.annotations.LoggerProvider;
import com.bivgroup.config.annotations.types.LoggerType;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Created by bush on 20.06.2016.
 */
public class DemoProducerCallback implements Callback {
    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        logger.debug(String.format("send message to %1 partititon %2 offset %3", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset()));
        if (e != null) {
            logger.debug("send err" + e);
            e.printStackTrace();
        }
    }
}