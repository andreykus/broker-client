package com.bivgroup.broker.annotations.factory.log;

/**
 * Created by bush on 12.05.2016.
 */

import com.bivgroup.broker.annotations.LoggerProvider;
import com.bivgroup.broker.annotations.types.LoggerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@LoggerProvider
class LogFactory {
    @Produces
    @LoggerProvider(type = LoggerType.Log4J)
    Logger createLoggerLog4J(InjectionPoint injectionPoint) {
        return LogManager.getLogger(injectionPoint.getMember().getDeclaringClass().getName());

    }

}