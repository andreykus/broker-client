package com.bivgroup.broker.mq.interfaces;


import com.bivgroup.broker.exceptions.MessageException;

/**
 * <p>Title: ProducerFactory</p>
 * <p>Description: </p>
 */
public interface ProducerFactory {

    <T> void addProducer(Producer<T> producer) throws MessageException;

    <T> Producer<T> getProducer(String producerKey) throws MessageException;

    void init() throws MessageException;

    void destroy() throws MessageException;
}
