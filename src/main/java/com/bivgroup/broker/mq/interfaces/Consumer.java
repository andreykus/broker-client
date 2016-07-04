package com.bivgroup.broker.mq.interfaces;

import com.bivgroup.broker.exceptions.MessageException;

/**
 * Потребитель сообщения
 * @param <T>
 */
public interface Consumer<T> {
    void shutdown() throws MessageException;

    void receive(MessageProcessor worker) throws MessageException;

    String getConsumerKey() throws MessageException;
}
