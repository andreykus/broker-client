package com.bivgroup.broker.mq.impl.rabbit;


import java.io.IOException;

/**
 * Created by bush on 14.10.2015.
 */
public class MailManager extends AbstractMessageManager {

    private static final String ROUTING_KEY = "1";


    @Override
    public <T> Boolean sendMessage(T message) throws IOException {

        channel.exchangeDeclare(EXCHANGE_NAME, TYPE);


        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.toString().getBytes());
        return Boolean.TRUE;
    }

    @Override
    public Object getMessage() throws IOException {
        channel.basicGet(EXCHANGE_NAME, true);
        return null;
    }

}
