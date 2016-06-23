package com.bivgroup.broker.mq.interfaces;

import com.bivgroup.broker.exceptions.MessageException;

/**
 * Created by bush on 23.06.2016.
 */
public interface MessageProcessor<T> {
    void process(T message) throws MessageException;
}
