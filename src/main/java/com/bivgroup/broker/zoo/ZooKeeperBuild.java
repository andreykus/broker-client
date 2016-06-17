package com.bivgroup.broker.zoo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;

/**
 * Created by bush on 16.06.2016.
 */
public class ZooKeeperBuild {
    private CuratorFramework curator;

    public ZooKeeperBuild() throws InterruptedException {
        this.curator = CuratorFrameworkFactory.newClient("localhost:2181,localhost:2182,localhost:2183", new RetryNTimes(
                Integer.MAX_VALUE, 1));


        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("++++++++++++");
                //System.out.println(event.getData());
               // System.out.println(event.getContext());
                System.out.println(event.getType());
                System.out.println("+++++++++++++");
            }
        };

        curator.getCuratorListenable().addListener(listener);
        this.curator.start();
        this.curator.getZookeeperClient().blockUntilConnectedOrTimedOut();

    }

    public void getInnfo() throws Exception {

        //curator.create().forPath("/test_node", "".getBytes());



        curator.setData().forPath("/test_node", "1".getBytes());
        //System.out.println(curator.getData().forPath("/test_node"));
        curator.getChildren().watched().forPath("/test_node");

        curator.setData().inBackground().forPath("/test_node", "2".getBytes());
        System.out.println(curator.getData().forPath("/test_node"));
        curator.setData().inBackground().forPath("/test_node", "311".getBytes());
        System.out.println(curator.getData().forPath("/test_node"));
        curator.getData().usingWatcher(new CuratorWatcher(){
            @Override
            public void process(WatchedEvent event) throws Exception {
                System.out.println("-------------");
                System.out.println(event.getWrapper().toString());
                System.out.println("-------------");
            }

        }).inBackground().forPath("/test_node");

        curator.setData().inBackground().forPath("/test_node", "32".getBytes());
        //System.out.println(curator.getData().forPath("/test_node"));
        curator.setData().inBackground().forPath("/test_node", "321".getBytes());
       // System.out.println(curator.getData().forPath("/test_node"));
        curator.setData().inBackground().forPath("/test_node", "322".getBytes());
        //System.out.println(curator.getData().forPath("/test_node"));
        curator.close();
    };
}



