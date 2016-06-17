package com.bivgroup.broker.annotations.factory.property;

/**
 * Created by bush on 12.05.2016.
 */

import com.bivgroup.broker.annotations.LoggerProvider;
import com.bivgroup.broker.annotations.PropertyProvider;
import com.bivgroup.broker.annotations.types.LoggerType;
import com.bivgroup.broker.annotations.types.PropertyFiles;
import com.bivgroup.broker.exceptions.MessageException;
import org.apache.logging.log4j.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Singleton
class PropertyFactory {

    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;

    @Produces
    @PropertyProvider(type = PropertyFiles.EXT)
    Properties createPropertyExt(InjectionPoint injectionPoint) throws MessageException {
        return getProperties(PropertyFiles.EXT.getFileName());

    }

    @Produces
    @PropertyProvider(type = PropertyFiles.IN)
    Properties createPropertyIn(InjectionPoint injectionPoint) throws MessageException {
        return getProperties(PropertyFiles.IN.getFileName());

    }

    /**
     * Получение свойств из файла
     */
    private Properties getProperties(String fileName) throws MessageException {
        InputStream input = null;
        Properties prop = new Properties();
        try {
            input = getClass().getClassLoader().getResourceAsStream(fileName);
            if (input == null) {
                logger.error(String.format("provider: %s file not found", fileName));
                throw new MessageException(String.format("provider: %s file not found", fileName));
            }
            prop.load(input);
        } catch (IOException ex) {
            logger.error(ex);
            throw new MessageException(ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(e);
                    throw new MessageException(e);
                }
            }
            return prop;
        }
    }

}