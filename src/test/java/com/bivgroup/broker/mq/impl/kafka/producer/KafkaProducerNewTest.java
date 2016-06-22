package com.bivgroup.broker.mq.impl.kafka.producer;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

/**
 * Created by bush on 21.06.2016.
 */
public class KafkaProducerNewTest {
    @Test
    public void testO() throws Exception {
        Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();
        KafkaProducerNew mp = weldContainer.instance().select(KafkaProducerNew.class).get();
        mp.send(null);
    }
}