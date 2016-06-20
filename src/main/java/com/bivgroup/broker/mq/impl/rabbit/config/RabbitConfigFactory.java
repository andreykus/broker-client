package com.bivgroup.broker.mq.impl.rabbit.config;

import com.bivgroup.broker.mq.MessageConfigProvider;
import com.bivgroup.broker.mq.MessageConfigType;
import com.bivgroup.config.Config;
import com.bivgroup.config.ConfigFactory;
import com.bivgroup.config.annotations.LoggerProvider;
import com.bivgroup.config.annotations.types.LoggerType;
import org.apache.logging.log4j.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by bush on 20.06.2016.
 */
@Singleton
@MessageConfigProvider
public class RabbitConfigFactory implements ConfigFactory {
    @Override
    public Config getConfig() {
        return new RabbitConfig();
    }

    @Override
    public void refreshConfig() {

    }


    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;

    @Produces
    @MessageConfigProvider(type = MessageConfigType.RABBIT)
    Config createRabbitConfig(InjectionPoint injectionPoint) {
        return getConfig();
    }


}
