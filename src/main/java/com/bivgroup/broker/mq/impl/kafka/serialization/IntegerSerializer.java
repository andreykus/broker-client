package com.bivgroup.broker.mq.impl.kafka.serialization;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by bush on 21.06.2016.
 */
public class IntegerSerializer implements Serializer<Integer> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

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
