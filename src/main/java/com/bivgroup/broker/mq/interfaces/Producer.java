
package com.bivgroup.broker.mq.interfaces;

import com.bivgroup.broker.exceptions.MessageException;

public interface Producer<T> {

    public abstract void send(T message) throws MessageException;

    public abstract String getProducerKey() throws MessageException;

}
