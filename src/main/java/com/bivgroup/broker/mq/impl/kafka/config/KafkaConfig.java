package com.bivgroup.broker.mq.impl.kafka.config;

import com.bivgroup.broker.mq.impl.kafka.constant.KafkaConstants;
import com.bivgroup.config.Config;

import java.util.Properties;

/**
 * Created by bush on 20.06.2016.
 */
public class KafkaConfig implements Config {
    Properties prop;

    @Override
    public Properties getProperties() {
        if (this.prop == null) {
            this.prop = new Properties();
            this.prop.setProperty(KafkaConstants.BROKER_LIST, "");
        }
        return prop;
    }
}
