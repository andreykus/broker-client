package com.bivgroup.broker.annotations.factory.locale;

/**
 * Created by bush on 12.05.2016.
 */

import com.bivgroup.broker.annotations.BundleProvider;
import com.bivgroup.broker.annotations.LoggerProvider;
import com.bivgroup.broker.annotations.types.LocaleType;
import com.bivgroup.broker.annotations.types.LoggerType;
import org.apache.logging.log4j.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Locale;
import java.util.ResourceBundle;

@BundleProvider
@Singleton
class BundleFactory {

    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;

    @Produces
    @BundleProvider(type = LocaleType.RU)
    ResourceBundle createResourceBundleRu(InjectionPoint injectionPoint) {
        logger.debug("Bundle for RU");
        return ResourceBundle.getBundle("systemMessages", new Locale("ru", "RU"));
    }

}