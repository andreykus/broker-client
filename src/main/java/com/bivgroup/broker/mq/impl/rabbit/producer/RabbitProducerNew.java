package com.bivgroup.broker.mq.impl.rabbit.producer;

import com.bivgroup.broker.exceptions.MessageException;
import com.bivgroup.broker.mq.interfaces.Message;
import com.bivgroup.broker.mq.interfaces.annotations.MessageConfigProvider;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProviderType;
import com.bivgroup.config.Config;
import com.bivgroup.config.annotations.BundleProvider;
import com.bivgroup.config.annotations.LoggerProvider;
import com.bivgroup.config.annotations.types.LoggerType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by bush on 20.06.2016.
 * Отправка сообщения RABIT
 */

public class RabbitProducerNew implements com.bivgroup.broker.mq.interfaces.Producer<Message> {

    static final String EXCHANGE_NAME = "message_in_rabbit";
    final static String TYPE = "direct";
    private static final String ROUTING_KEY = "1";
    @Inject
    @MessageConfigProvider(type = MessageProviderType.RABBIT)
    public Config config;
    protected Channel channel;
    private String host;
    private String port;
    private String nameQueue;
    private String nameExchange;
    private Connection connect;
    private ConnectionFactory connectFactory;
    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;
    @Inject
    @BundleProvider
    private ResourceBundle bundle;


    private void sendMessage(Message message) throws MessageException {
        try {
            channel.exchangeDeclare(EXCHANGE_NAME, TYPE);
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getPayload());
        } catch (IOException e) {
            throw new MessageException(e);
        }
    }

    @Override
    public void send(Message message) throws MessageException {
        try {
            init();
            connect();
            sendMessage(message);
            close();
        } catch (IOException e) {
            throw new MessageException(e);
        }
    }

    @Override
    public void close() throws MessageException {
        try {
            channel.close();
            connect.close();
        } catch (IOException e) {
            throw new MessageException(e);
        }
    }

    @Override
    public String getProducerKey() throws MessageException {
        return null;
    }


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
