package com.bivgroup.broker.annotations;

import com.bivgroup.broker.annotations.types.OutSystem;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by bush on 11.05.2016.
 */
@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface BlProvider {
    OutSystem type() default OutSystem.DOTNET;
}