package com.bivgroup.common.interfaces;

/**
 * Created by bush on 15.07.2016.
 */
public interface Function<T, U> {
    U apply(T arg);
}
