package com.bivgroup.broker.mq.impl.rabbit.consumer;

import com.bivgroup.broker.exceptions.MessageException;
import com.bivgroup.broker.mq.common.Message;
import com.bivgroup.broker.mq.interfaces.annotations.MessageConfigProvider;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProviderType;
import com.bivgroup.config.Config;
import com.bivgroup.config.annotations.LoggerProvider;
import com.bivgroup.config.annotations.types.LoggerType;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.rabbitmq.client.*;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by bush on 21.06.2016.
 */
public class RabbitConsumerNew implements com.bivgroup.broker.mq.interfaces.Consumer<Message> {

    @Inject
    @MessageConfigProvider(type = MessageProviderType.RABBIT)
    public Config configPr;

    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;

    private ExecutorService executor;

    private volatile boolean running = false;
    private List<Future<?>> runners = new ArrayList<Future<?>>();

    private AtomicLong pausedTime = new AtomicLong(0);

    public static long MAX_PAUSE = 1000; // not final for the test
    private final int readers = 1;

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


    private void initReciver() throws IOException {
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


    public void start() throws Exception {

        executor = Executors.newCachedThreadPool(
                new ThreadFactoryBuilder().setNameFormat("RabbitConsumer-%d").build());

        logger.info("start recive message");

        running = true;

        runners.add(
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        while (running) {
                            logger.info("calculate message");
                            try {
                                long pause = Math.min(pausedTime.get(), MAX_PAUSE);
                                if (pause > 0) {
                                    Thread.sleep(pause);
                                    pausedTime.set(0);
                                }

                                initReciver();
                                //channel.basicGet(EXCHANGE_NAME, true);
                                byte[] message = {1, 2, 3};

                            } catch (Exception e) {
                                logger.error("Exception on consuming with topic: ", e);
                            }
                        }
                    }
                })
        );
    }

    @Override
    public void shutdown() throws Exception {
        stop();
        channel.close();
        connect.close();
    }


    public void setPause(long ms) {
        pausedTime.addAndGet(ms);
    }

    private void stop() {
        running = false;
        try {
            for (Future<?> runner : runners) {
                runner.get();
            }
        } catch (InterruptedException e) {
            // do nothing
        } catch (ExecutionException e) {
            // log.error("Exception on stopping the task", e);
        }
    }

    @Override
    public void receive() throws Exception {
        start();
    }

    @Override
    public String getConsumerKey() throws MessageException {
        return null;
    }
}
