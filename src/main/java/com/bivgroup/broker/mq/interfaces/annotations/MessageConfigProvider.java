package com.bivgroup.broker.mq.interfaces.annotations;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by bush on 22.06.2016.
 */
@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface MessageConfigProvider {
    MessageProviderType type() default MessageProviderType.KAFKA;
}