package com.bivgroup.broker.mq.interfaces;

import com.bivgroup.broker.exceptions.MessageException;

public interface Producer<T> {

    void send(T message) throws MessageException;

    String getProducerKey() throws MessageException;

    void close() throws MessageException;

}
