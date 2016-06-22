package com.bivgroup.config;

/**
 * Created by bush on 20.06.2016.
 */
public interface ConfigFactory {
    Config getConfig();

    void refreshConfig();
}
