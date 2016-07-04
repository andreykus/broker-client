package com.bivgroup.broker.mq.impl.rabbit.config;

import com.bivgroup.config.Config;

import java.util.Properties;

/**
 * Created by bush on 20.06.2016.
 */

public class RabbitConfig implements Config {
    private Properties prop;

    @Override
    public Properties getProperties() {
        if (this.prop == null) {
            this.prop = new Properties();

        }
        return prop;
    }

    @Override
    public void setProperties() {
        if (this.prop == null) {
            this.prop = new Properties();
        }
    }

}
