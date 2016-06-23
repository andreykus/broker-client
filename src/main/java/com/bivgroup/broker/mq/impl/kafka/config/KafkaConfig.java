package com.bivgroup.broker.mq.impl.kafka.config;

import com.bivgroup.broker.mq.impl.kafka.constant.KafkaConstants;
import com.bivgroup.broker.mq.impl.kafka.monitor.MonitorKafka;
import com.bivgroup.config.Config;
import com.google.common.collect.Lists;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by bush on 20.06.2016.
 */

public class KafkaConfig implements Config {
    Properties prop;

    private void setMyReporter() {
        prop.put("metric.reporters", Lists.newArrayList(MonitorKafka.class.getName()));
        // this should be needed because ProducerConfig cannot retrieve undefined key
        try {
            Field f = kafka.producer.ProducerConfig.class.getDeclaredField("config");
            f.setAccessible(true);
            ConfigDef config = (ConfigDef) f.get(ConfigDef.class);
            config.define(MonitorKafka.class.getName(), ConfigDef.Type.CLASS, MonitorKafka.class, ConfigDef.Importance.LOW, "");
        } catch (Exception e) {
        }

    }


    @Override
    public Properties getProperties() {
        if (this.prop == null) {
            this.prop = new Properties();
            //producer for consumer
            this.prop.setProperty(KafkaConstants.BROKER_LIST, "localhost:9092");
            this.prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            this.prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");//ByteArraySerializer
            this.prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "com.bivgroup.broker.mq.impl.kafka.serialization.IntegerSerializer");
            //require for consumer
            this.prop.setProperty(KafkaConstants.ZOOKEEPER_LIST, "localhost:2181");
            this.prop.setProperty(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "test");
        }

        setMyReporter();
        return prop;
    }

    @Override
    public void setProperties() {
        if (this.prop == null) {
            this.prop = new Properties();
        }
    }
}
