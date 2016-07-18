package com.bivgroup.broker.mq.common.log.appender;

/**
 * Created by bush on 18.07.2016.
 */

import com.bivgroup.broker.mq.impl.kafka.producer.KafkaProducerNew;
import com.bivgroup.broker.mq.interfaces.Message;
import com.bivgroup.broker.mq.interfaces.Producer;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Plugin(name = "MessageAppender", category = "Core", elementType = "appender", printObject = true)
public class MessageAppender extends AbstractAppender {


    public Producer producer = new KafkaProducerNew();
    private String identifier = null;
    private String type = "direct";
    private boolean durable = false;
    private String routingKey = "";

    private ExecutorService threadPool = Executors.newSingleThreadExecutor();


    protected MessageAppender(String name, Filter filter,
                              Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @PluginFactory
    public static MessageAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("otherAttribute") String otherAttribute) {
        if (name == null) {
            LOGGER.error("No name provided for MessageAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return new MessageAppender(name, filter, layout, true);
    }

    @Override
    public void append(LogEvent event) {
        threadPool.submit(new AppenderTask(event, getLayout()));
    }

    class AppenderTask implements Callable<LogEvent> {
        LogEvent loggingEvent;
        Layout<? extends Serializable> layout;

        AppenderTask(LogEvent loggingEvent, Layout<? extends Serializable> layout) {
            this.loggingEvent = loggingEvent;
            this.layout = layout;
        }

        private void callAppender(final LogEvent event, Layout layout) {
            try {
                String id = String.format("%s:%s", identifier, System.currentTimeMillis());
                Message message = new com.bivgroup.broker.mq.common.Message(routingKey, layout.toByteArray(event));
                producer.send(message);
            } catch (final Exception ex) {
                throw new AppenderLoggingException(ex);
            }
        }

        @Override
        public LogEvent call() throws Exception {
            callAppender(loggingEvent, layout);
            return loggingEvent;
        }
    }
}