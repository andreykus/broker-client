package com.bivgroup.broker.mq.common;

import com.bivgroup.broker.mq.interfaces.MessageQueue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Очередь сообщений в памяти на основе блокирующей очереди
 */
public class MemoryQueue implements MessageQueue {

    private final BlockingQueue<Message> queue;
    private final int capacity;


    public MemoryQueue(int capacity) {
        this.capacity = capacity;
        if (capacity == 0) {
            this.queue = new SynchronousQueue();
        } else {
            this.queue = new ArrayBlockingQueue(capacity);
        }
    }

    @Override
    public boolean offer(Message msg) {
        if (capacity == 0) {
            try {
                // for synchronous queue, this will block until the sink takes the message from the queue
                queue.put(msg);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        } else {
            return queue.offer(msg);
        }
    }

    @Override
    public Message poll(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return queue.poll(timeout, timeUnit);
    }

    @Override
    public int drain(int batchSize, List<Message> msgList) {
        return queue.drainTo(msgList, batchSize);
    }

    @Override
    public void close() {
        // do nothing
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public long size() {
        return queue.size();
    }

    @Override
    public long remainingCapacity() {
        return queue.remainingCapacity();
    }
}
