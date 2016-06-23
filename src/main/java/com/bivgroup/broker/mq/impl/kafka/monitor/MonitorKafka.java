package com.bivgroup.broker.mq.impl.kafka.monitor;

import com.bivgroup.config.annotations.LoggerProvider;
import com.bivgroup.config.annotations.types.LoggerType;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.metrics.KafkaMetric;
import org.apache.kafka.common.metrics.MetricsReporter;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by bush on 23.06.2016.
 */
public class MonitorKafka implements MetricsReporter, Runnable {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder().setDaemon(false).setNameFormat("KafkaReporter-%d").build());
    @Inject
    @LoggerProvider(type = LoggerType.Log4J)
    private transient Logger logger;
    private List<KafkaMetric> metricList = Collections.synchronizedList(new ArrayList<KafkaMetric>());
    private MyConfig config;


    @Override
    public void init(List<KafkaMetric> metrics) {

        if (config.getBoolean(MyConfig.REPORTER_ENABLED)) {
            final int interval = config.getInt(MyConfig.INTERVAL);

            for (final KafkaMetric metric : metrics) {
                metricList.add(metric);
            }

            scheduler.scheduleAtFixedRate(this, interval, interval, TimeUnit.SECONDS);
        }

    }


    @Override
    public void metricChange(final KafkaMetric metric) {
        metricList.add(metric);
    }


    @Override
    public void close() {
        scheduler.shutdownNow();
    }

    @Override
    public void configure(Map<String, ?> configs) {
        this.config = new MyConfig(configs);

    }

    @Override
    public void run() {
        for (KafkaMetric metric : metricList) {
            logger.info(String.format("metric %s value %d", metric.metricName(), metric.value()));
        }
    }

    public static class MyConfig extends AbstractConfig {
        public static final String REPORTER_ENABLED = "kafka.graphite.metrics.reporter.enabled";
        public static final String PREFIX = "kafka.my.metrics.prefix";
        public static final String INCLUDE = "kafka.my.metrics.include";
        public static final String EXCLUDE = "kafka.my.metrics.exclude";
        public static final String INTERVAL = "kafka.metrics.polling.interval.secs";

        private static final ConfigDef configDefinition = new ConfigDef()
                .define(REPORTER_ENABLED, ConfigDef.Type.BOOLEAN, false, ConfigDef.Importance.LOW, "Enables actual plugin")
                .define(PREFIX, ConfigDef.Type.STRING, "kafka", ConfigDef.Importance.MEDIUM, "The metric prefix that's sent with metric names")
                .define(INCLUDE, ConfigDef.Type.STRING, "", ConfigDef.Importance.LOW, "A regular expression allowing explicitly include certain metrics")
                .define(EXCLUDE, ConfigDef.Type.STRING, "", ConfigDef.Importance.LOW, "A regular expression allowing you to exclude certain metrics")
                .define(INTERVAL, ConfigDef.Type.INT, "60", ConfigDef.Importance.MEDIUM, "Polling interval that will be used for all Kafka metrics");

        private MyConfig(Map<?, ?> originals) {
            super(configDefinition, originals);
        }
    }
}
