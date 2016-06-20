
package com.bivgroup.broker.mq.impl.kafka.constant;


/**
 * <p>Title: KafkaConstants</p>
 * <p>Description: </p>
 */
public interface KafkaConstants {
    //compression.codec,key.serializer.class,batch.num.messages

    //Список брокеров
    String BROKER_LIST = "metadata.broker.list";
    //Тип создателя сообщений (sync,async)
    String PRODUCER_TYPE = "producer.type";
    //Класс сериализатора объекта ("kafka.serializer.StringEncoder")
    String SERIALIZER_CLASS = "serializer.class";
    //Класс сериализатора ключа объекта
    String KEY_SERIALIZER_CLASS = "key.serializer.class";
    //Идентификатор клиента
    String CLIENT_ID = "client.id";
    //Идентификатор группы
    String GROUP_ID = "group.id";
    //Авто коммит сообщения
    String AUTO_COMMIT_ENABLE = "auto.commit.enable";
    String AUTO_COMMIT_INTERVAL = "auto.commit.interval.ms";
    //Авто сброс смещения
    String AUTO_OFFSET_RESET = "auto.offset.reset";
    //Требовать подтверждения ("1")
    String REQUEST_REQUIRED_ACKS = "request.required.acks";
    //Класс расположения раздела ("com.bivgroup.broker.mq.impl.kafka")
    String PARTITIONER_CLASS = "partitioner.class";

    String BATCH_NUM_MESSAGE = "batch.num.messages";
    String CONSUMER_TIMEOUT = "consumer.timeout.ms";
    String CONSUMER_REQUEST_TIMEOUT = "consumer.request.timeout.ms";


    //Список коннектов к ZOOKEEPER
    String ZOOKEEPER_LIST = "zookeeper.connect";
    String ZOOKEEPER_SESSION_TIMEOUT = "zookeeper.session.timeout.ms";
    String ZOOKEEPER_SYNCHRONYZE_TIME = "zookeeper.sync.time.ms";


//                    "consumer.request.timeout.ms"


}
