package com.bivgroup.broker.mq.impl.rabbit.consumer;

import com.bivgroup.broker.exceptions.MessageException;
import com.bivgroup.broker.mq.common.Message;
import com.bivgroup.broker.mq.interfaces.annotations.MessageConfigProvider;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProviderType;
import com.bivgroup.config.Config;
import com.bivgroup.config.annotations.LoggerProvider;
import com.bivgroup.config.annotations.types.LoggerType;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import kafka.consumer.*;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by bush on 21.06.2016.
 */
public class KafkaConsumerNew implements com.bivgroup.broker.mq.interfaces.Consumer<Message> {

    @Inject
    @MessageConfigProvider(type = MessageProviderType.KAFKA)
    public Config configPr;

    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;

    private ExecutorService executor;
    private ConsumerConnector connector;
    private volatile boolean running = false;
    private List<Future<?>> runners = new ArrayList<Future<?>>();

    private AtomicLong pausedTime = new AtomicLong(0);

    public static long MAX_PAUSE = 1000; // not final for the test
    private final int readers = 1;

    public void start() throws Exception {
        String topic = "mytesttopic1";
        ConsumerConfig config = new ConsumerConfig(configPr.getProperties());

        executor = Executors.newCachedThreadPool(
                new ThreadFactoryBuilder().setNameFormat("KafkaConsumer-%d").build());
        connector = Consumer.createJavaConsumerConnector(config);

        final Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(ImmutableMap.of(topic, readers));

        logger.info("start recive message");

        final List<KafkaStream<byte[], byte[]>> streamList = streams.get(topic);
        if (streamList == null) {
            throw new RuntimeException(topic + " is not valid");
        }

        running = true;
        for (KafkaStream<byte[], byte[]> stream : streamList) {
            logger.info(stream.clientId());

            final ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
            logger.info(iterator.next().message());

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
                                    byte[] message = iterator.next().message();
//                                    router.process(
//                                            KafkaConsumerNew.this,
//                                            new DefaultMessageContainer(new Message(topic, message), jsonMapper));
                                } catch (ConsumerTimeoutException timeoutException) {
                                    // do nothing
                                } catch (Exception e) {
                                    //log.error("Exception on consuming kafka with topic: " + topic, e);
                                }
                            }
                        }
                    })
            );
        }
    }


    public void shutdown() {
        stop();
        connector.shutdown();
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
        shutdown();
    }

    @Override
    public String getConsumerKey() throws MessageException {
        return null;
    }
}
