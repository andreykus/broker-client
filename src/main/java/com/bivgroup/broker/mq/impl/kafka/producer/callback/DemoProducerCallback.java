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
        logger.info("send" + recordMetadata);
        if (e != null) {
            logger.info("send err" + e);
            e.printStackTrace();
        }
    }
}