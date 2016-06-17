
package com.bivgroup.broker.mq.interfaces;


import com.bivgroup.broker.exceptions.MessageException;

/**
 * <p>Title: ConsumerFactory</p>
 * <p>Description: 消费者工厂接口</p>
 *
 * @author Victor.Zxy
 * @version 1.0
 * @since 2015-06-01
 */
public interface ConsumerFactory {

    public <T> void addConsumer(Consumer<T> consumer) throws MessageException;


    public <T> Consumer<T> getConsumer(String consumerKey) throws MessageException;

    public void init() throws MessageException;

    public void destroy() throws MessageException;

}
