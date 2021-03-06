package com.bivgroup.broker.mq.impl.kafka.consumer;

import com.bivgroup.broker.exceptions.MessageException;
import com.bivgroup.broker.mq.common.Message;
import com.bivgroup.broker.mq.interfaces.MessageProcessor;
import com.bivgroup.broker.mq.interfaces.annotations.MessageConfigProvider;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProviderType;
import com.bivgroup.config.Config;
import com.bivgroup.config.annotations.BundleProvider;
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
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by bush on 21.06.2016.
 */
public class KafkaConsumerNew implements com.bivgroup.broker.mq.interfaces.Consumer<Message> {

    public static long MAX_PAUSE = 1000; // not final for the test
    private final int readers = 1;

    @Inject
    @MessageConfigProvider(type = MessageProviderType.KAFKA)
    public Config configProperty;

    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;

    @Inject
    @BundleProvider
    private ResourceBundle bundle;

    private ExecutorService executor;
    private ConsumerConnector connector;
    private volatile boolean running = false;
    private List<Future<?>> runners = new ArrayList<Future<?>>();
    private AtomicLong pausedTime = new AtomicLong(0);


    public void start(final MessageProcessor worker) throws MessageException {
        String topic = "mytesttopic1";
        ConsumerConfig config = new ConsumerConfig(configProperty.getProperties());

        executor = Executors.newCachedThreadPool(
                new ThreadFactoryBuilder().setNameFormat(bundle.getString("message.kafka.consumer.thread")).build());
        connector = Consumer.createJavaConsumerConnector(config);

        final Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(ImmutableMap.of(topic, readers));

        logger.debug(bundle.getString("message.kafka.start.recive"));

        final List<KafkaStream<byte[], byte[]>> streamList = streams.get(topic);
        if (streamList == null) {
            throw new RuntimeException(String.format(bundle.getString("message.kafka.topic.empty"), topic));
        }

        running = true;
        for (final KafkaStream<byte[], byte[]> stream : streamList) {
            logger.debug(String.format(bundle.getString("message.kafka.consumer.clientid"), stream.clientId()));

            final ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
            logger.debug(iterator.next().message());

            runners.add(
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            while (running) {

                                try {
                                    long pause = Math.min(pausedTime.get(), MAX_PAUSE);
                                    if (pause > 0) {
                                        Thread.sleep(pause);
                                        pausedTime.set(0);
                                    }

                                    byte[] message = iterator.next().message();

                                    logger.debug(message);

                                    com.bivgroup.broker.mq.interfaces.Message mes = new Message("", message);

                                    worker.process(mes);

                                } catch (ConsumerTimeoutException timeoutException) {

                                    logger.error(timeoutException);
                                    //Thread.interrupted();
                                } catch (Exception e) {

                                    logger.error(e);
                                }

                            }
                        }
                    })
            );
        }
    }

    @Override
    public void shutdown() throws MessageException {
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
    public void receive(MessageProcessor worker) throws MessageException {
        start(worker);
    }

    @Override
    public String getConsumerKey() throws MessageException {
        return null;
    }
}
