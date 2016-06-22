package com.bivgroup.broker.mq.interfaces;

import com.bivgroup.broker.exceptions.MessageException;

public interface Consumer<T> {

    public abstract void shutdown() throws MessageException;

    public abstract void receive() throws MessageException;

    public abstract String getConsumerKey() throws MessageException;
}
