
package com.bivgroup.broker.mq.interfaces;


import com.bivgroup.broker.exceptions.MessageException;

/**
 * <p>Title: ConsumerFactory</p>
 * <p>Description: </p>
 *
 */
public interface ConsumerFactory {

    public <T> void addConsumer(Consumer<T> consumer) throws MessageException;


    public <T> Consumer<T> getConsumer(String consumerKey) throws MessageException;

    public void init() throws MessageException;

    public void destroy() throws MessageException;

}
