package com.bivgroup.config.annotations;

import com.bivgroup.config.annotations.types.LoggerType;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by bush on 12.05.2016.
 */
@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface LoggerProvider {
    LoggerType type() default LoggerType.Log4J;
}
