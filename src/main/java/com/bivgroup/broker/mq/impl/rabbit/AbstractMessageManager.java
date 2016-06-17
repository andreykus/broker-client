package com.bivgroup.broker.mq.impl.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by bush on 14.10.2015.
 */
public abstract class AbstractMessageManager {
    static final String EXCHANGE_NAME = "message_in_rabbit";
    final static String TYPE = "direct";

    private String host;
    private String port;
    private String nameQueue;

    private String nameExchange;
    private Connection connect;

    private ConnectionFactory connectFactory;
    protected Channel channel;


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

    public abstract <T> Boolean sendMessage(T message) throws IOException;

    public abstract Object getMessage() throws IOException;

    public <T> void send(T message) throws IOException {
        init();
        connect();
        sendMessage(message);
        channel.close();
        connect.close();
    }

    public void initReciver() throws IOException {
        init();
        connect();

        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, String.valueOf(""));

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");

                try {

                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };
        channel.basicQos(5);
        channel.basicConsume(queueName, false, consumer);
    }

    public Object get() throws IOException {
        init();
        connect();
        Object message = getMessage();
        channel.close();
        connect.close();
        return message;
    }


}
