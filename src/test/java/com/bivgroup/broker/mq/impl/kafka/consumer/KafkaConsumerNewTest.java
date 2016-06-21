package com.bivgroup.broker.mq.impl.kafka.consumer;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

/**
 * Created by bush on 21.06.2016.
 */

public class KafkaConsumerNewTest {

    @Test
    public void testReceive() throws Exception {
        Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();
        KafkaConsumerNew mp = weldContainer.instance().select(KafkaConsumerNew.class).get();
        mp.receive();
    }
}