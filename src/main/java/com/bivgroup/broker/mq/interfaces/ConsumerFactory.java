package com.bivgroup.broker.mq.interfaces;


import com.bivgroup.broker.exceptions.MessageException;

/**
 * <p>Title: ConsumerFactory</p>
 * <p>Description: </p>
 */
public interface ConsumerFactory {

    <T> void addConsumer(Consumer<T> consumer) throws MessageException;

    <T> Consumer<T> getConsumer(String consumerKey) throws MessageException;

    void init() throws MessageException;

    void destroy() throws MessageException;

}
