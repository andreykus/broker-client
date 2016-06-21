package com.bivgroup.broker.mq;


import java.util.List;
import java.util.concurrent.TimeUnit;


public interface MessageQueue {
    boolean offer(Message msg);

    Message poll(long timeout, TimeUnit timeUnit) throws InterruptedException;

    int drain(int batchSize, List<Message> msgList);

    void close();

    boolean isEmpty();

    long size();

    long remainingCapacity();
}
