
package com.bivgroup.broker.mq.interfaces;


import com.bivgroup.broker.exceptions.MessageException;

/**
 * <p>Title: ProducerFactory</p>
 * <p>Description: </p>
 *
 */
public interface ProducerFactory {

    public <T> void addProducer(Producer<T> producer) throws MessageException;

    public <T> Producer<T> getProducer(String producerKey) throws MessageException;

    public void init() throws MessageException;

    public void destroy() throws MessageException;
}
