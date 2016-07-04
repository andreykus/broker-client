package com.bivgroup.config.annotations.factory.log;

/**
 * Created by bush on 12.05.2016.
 * Фабрика лога
 */

import com.bivgroup.config.annotations.LoggerProvider;
import com.bivgroup.config.annotations.types.LoggerType;
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