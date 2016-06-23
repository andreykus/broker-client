package com.bivgroup.broker;

import com.bivgroup.broker.mq.common.DemoMessageProcessor;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bush on 22.06.2016.
 */


public class ManagerMqTest {

    public ManagerMq mq;
    public WeldContainer container;

    @Before
    public void start() {
        Weld weld = new Weld();
        container = weld.initialize();
        mq = container.instance().select(ManagerMq.class).get();
        container.shutdown();
    }

    @Test
    public void testSendMessage() throws Exception {

        com.bivgroup.broker.mq.interfaces.Message messageIn = new com.bivgroup.broker.mq.common.Message("2", "my test message".getBytes());
        mq.sendMessage(messageIn);
    }

    @Test
    public void testReciveMessage() throws Exception {
        mq.subscribe(new DemoMessageProcessor());
    }
}
