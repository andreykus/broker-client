package com.bivgroup.broker.mq.interfaces;

import com.bivgroup.broker.exceptions.MessageException;

public interface Consumer<T> {


    public abstract void receive() throws Exception;

    public abstract String getConsumerKey() throws MessageException;
}
