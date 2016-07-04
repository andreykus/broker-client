package com.bivgroup.broker.mq.impl.kafka.message;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;


public class SimplePartitioner implements Partitioner {

    public SimplePartitioner(VerifiableProperties properties) {
    }

    @Override
    public int partition(Object key, int numberOfPartitions) {
        int partition = 0;
        int intKey = (Integer) key;
        if (intKey > 0) {
            partition = intKey % numberOfPartitions;
        }


        return partition;
    }


}