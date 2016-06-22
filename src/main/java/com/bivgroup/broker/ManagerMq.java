package com.bivgroup.broker;

import com.bivgroup.broker.exceptions.MessageException;
import com.bivgroup.broker.mq.common.Message;
import com.bivgroup.broker.mq.interfaces.Producer;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProvider;

import javax.inject.Inject;

/**
 * Created by bush on 22.06.2016.
 */

public class ManagerMq {

    @Inject
    @MessageProvider
    Producer producer;

    public void sendMessage(Message mes) throws MessageException {
        producer.send(null);
    }

}
