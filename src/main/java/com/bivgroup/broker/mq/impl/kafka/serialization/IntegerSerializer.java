package com.bivgroup.broker.mq.impl.kafka.serialization;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Сериализатор типа Integer
 * @see Integer
 * Created by bush on 21.06.2016.
 */
public class IntegerSerializer implements Serializer<Integer> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    /**
     * Метод сериализации
     * преобразует Integer в массив байт
     *
     * @param topic тема
     * @param data  величина сериализации
     * @return массив байт
     * @see Integer
     */
    @Override
    public byte[] serialize(String topic, Integer data) {
        if (data == null)
            return null;
        else {
            byte[] mas = {data.byteValue()};
            return mas;
        }
    }

    @Override
    public void close() {
        // nothing to do
    }
}
