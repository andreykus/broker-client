package com.bivgroup.broker.mq.common;

import com.bivgroup.broker.exceptions.MessageException;
import com.bivgroup.broker.mq.interfaces.MessageProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by bush on 23.06.2016.
 */

public class DemoMessageProcessor implements MessageProcessor<com.bivgroup.broker.mq.interfaces.Message> {

    private transient Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void process(com.bivgroup.broker.mq.interfaces.Message mes) throws MessageException {
        logger.info(String.format("Message worker: %s", mes.getPayload()));
    }

}
