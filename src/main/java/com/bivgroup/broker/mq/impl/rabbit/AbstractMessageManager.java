package com.bivgroup.broker.mq.impl.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by bush on 14.10.2015.
 */
public abstract class AbstractMessageManager {

    static final String EXCHANGE_NAME = "message_in_rabbit";
    final static String TYPE = "direct";
    protected Channel channel;
    private String host;
    private String port;
    private String nameQueue;
    private String nameExchange;
    private Connection connect;
    private ConnectionFactory connectFactory;

    private void connect() throws IOException {
        connectFactory = new ConnectionFactory();
        connectFactory.setHost(host);
        connect = connectFactory.newConnection();
        channel = connect.createChannel();
    }

    private void init() throws IOException {
        host = "";
        port = "";
    }


}
