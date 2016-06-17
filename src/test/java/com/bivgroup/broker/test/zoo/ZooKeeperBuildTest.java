package com.bivgroup.broker.test.zoo;

import com.bivgroup.test.utils.WeldJUnit4Runner;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Created by bush on 16.06.2016.
 */
@RunWith(WeldJUnit4Runner.class)
@FixMethodOrder(MethodSorters.JVM)
public class ZooKeeperBuildTest {

    @Test
    public void testGetCurator() throws Exception {
        ZooKeeperBuild b = new ZooKeeperBuild();
        b.getInnfo();


    }
}