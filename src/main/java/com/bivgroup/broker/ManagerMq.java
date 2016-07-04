package com.bivgroup.broker;

import com.bivgroup.broker.exceptions.MessageException;
import com.bivgroup.broker.mq.interfaces.Consumer;
import com.bivgroup.broker.mq.interfaces.Message;
import com.bivgroup.broker.mq.interfaces.MessageProcessor;
import com.bivgroup.broker.mq.interfaces.Producer;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProvider;

import javax.inject.Inject;

/**
 * Created by bush on 22.06.2016.
 * Менеджер сообщение
 */

public class ManagerMq {

    @Inject
    @MessageProvider
    private Producer producer;

    @Inject
    @MessageProvider
    private Consumer consumer;

    /**
     * Отослать сообщение
     *
     * @param mes
     * @throws MessageException
     */
    public void sendMessage(Message mes) throws MessageException {
        producer.send(mes);
    }

    /**
     * Оформить подписку на сообщения
     * @param worker
     * @throws MessageException
     */
    public void subscribe(MessageProcessor worker) throws MessageException {
        consumer.receive(worker);
    }

}
