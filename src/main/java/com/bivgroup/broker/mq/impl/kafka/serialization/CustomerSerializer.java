package com.bivgroup.broker.mq.impl.kafka.serialization;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by bush on 20.06.2016.
 * Типовой пример сериализатора и десериализатора объекта для kafka
 */
public class CustomerSerializer implements Serializer<Customer>, Deserializer<Customer> {

    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Customer data) {
        try {
            byte[] serializedName;
            int stringSize;
            if (data == null)
                return null;
            else {
                if (data.getName() != null) {
                    serializedName = data.getName().getBytes(UTF8_CHARSET);
                    stringSize = serializedName.length;
                } else {
                    serializedName = new byte[0];
                    stringSize = 0;
                }
            }
            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + stringSize);
            buffer.putInt(data.getID());
            buffer.putInt(stringSize);
            buffer.put(serializedName);

            return buffer.array();
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Customer to byte[] " + e);
        }
    }

    @Override
    public Customer deserialize(String topic, byte[] data) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            Integer id = buffer.getInt();
            Integer size = buffer.getInt();
            byte[] serializedName = new byte[size - 4 - 4];
            buffer.get(serializedName);
            String name = new String(serializedName, UTF8_CHARSET);
            Customer deserializedObject = new Customer(id, name);
            return deserializedObject;
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing to byte[] to Customer" + e);
        }
    }

    @Override
    public void close() {
        // nothing to close
    }
}
