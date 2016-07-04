package com.bivgroup.broker.mq.impl.rabbit.config;

import com.bivgroup.broker.mq.interfaces.annotations.MessageConfigProvider;
import com.bivgroup.broker.mq.interfaces.annotations.MessageProviderType;
import com.bivgroup.config.Config;
import com.bivgroup.config.ConfigFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;

/**
 * Created by bush on 20.06.2016.
 */
@Singleton
@MessageConfigProvider
public class RabbitConfigFactory implements ConfigFactory {

    @Produces
    @MessageConfigProvider(type = MessageProviderType.RABBIT)
    Config createRabbitConfig(InjectionPoint injectionPoint) {
        return getConfig();
    }

    @Override
    public Config getConfig() {
        return new RabbitConfig();
    }

    @Override
    public void refreshConfig() {

    }

}
