package com.bivgroup.broker.mq.interfaces;


import com.bivgroup.broker.mq.common.Message;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Очередь сообщений
 */
public interface MessageQueue {

    boolean offer(com.bivgroup.broker.mq.common.Message msg);

    Message poll(long timeout, TimeUnit timeUnit) throws InterruptedException;

    int drain(int batchSize, List<Message> msgList);

    void close();

    boolean isEmpty();

    long size();

    long remainingCapacity();
}
