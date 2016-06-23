package com.bivgroup.config.annotations.factory.locale;

/**
 * Created by bush on 12.05.2016.
 */

import com.bivgroup.config.annotations.BundleProvider;
import com.bivgroup.config.annotations.LoggerProvider;
import com.bivgroup.config.annotations.types.LocaleType;
import com.bivgroup.config.annotations.types.LoggerType;
import org.apache.logging.log4j.Logger;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
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
    @Default
    @BundleProvider(type = LocaleType.RU)
    ResourceBundle createResourceBundleRu(InjectionPoint injectionPoint) {
        logger.debug("Bundle for RU");
        return ResourceBundle.getBundle("systemMessages", new Locale("ru", "RU"));
    }

    @Produces
    @Alternative
    @BundleProvider(type = LocaleType.EN)
    ResourceBundle createResourceBundleEn(InjectionPoint injectionPoint) {
        logger.debug("Bundle for EN");
        return ResourceBundle.getBundle("systemMessages", new Locale("en", "EN"));
    }

}