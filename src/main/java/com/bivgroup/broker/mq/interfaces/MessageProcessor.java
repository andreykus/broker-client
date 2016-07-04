package com.bivgroup.broker.mq.interfaces;

import com.bivgroup.broker.exceptions.MessageException;

/**
 * Created by bush on 23.06.2016.
 * Интерфейс обработчика сообщения
 *
 * @param <T> тип сообщения
 */
public interface MessageProcessor<T> {
    /**
     * Обработать сообщение
     *
     * @param message сообщение
     * @throws MessageException
     * @see MessageException
     */
    void process(T message) throws MessageException;
}
