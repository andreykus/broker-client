
package com.bivgroup.broker.mq.interfaces;

import com.bivgroup.broker.exceptions.MessageException;

public interface Consumer<T> {


    public abstract void receive(T message) throws MessageException;

    public abstract String getConsumerKey() throws MessageException;
}
