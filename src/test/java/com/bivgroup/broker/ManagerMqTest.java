package com.bivgroup.broker;

import junit.framework.TestCase;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

/**
 * Created by bush on 22.06.2016.
 */
public class ManagerMqTest extends TestCase {
    @Test
    public void testSendMessage() throws Exception {
        Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();
        ManagerMq mp = weldContainer.instance().select(ManagerMq.class).get();
        mp.sendMessage(null);
    }
}
