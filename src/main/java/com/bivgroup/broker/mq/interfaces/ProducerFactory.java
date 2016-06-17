
package com.bivgroup.broker.mq.interfaces;


import com.bivgroup.broker.exceptions.MessageException;

/**
 * <p>Title: ProducerFactory</p>
 * <p>Description: 生产者工厂接口</p>
 *
 * @author Victor.Zxy
 * @version 1.0
 * @since 2015-06-01
 */
public interface ProducerFactory {

    public <T> void addProducer(Producer<T> producer) throws MessageException;

    public <T> Producer<T> getProducer(String producerKey) throws MessageException;

    public void init() throws MessageException;

    public void destroy() throws MessageException;
}
