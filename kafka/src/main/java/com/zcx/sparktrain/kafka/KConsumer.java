package com.zcx.sparktrain.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author zoucaoxin
 * @date 2019/5/30
 * @description kafka 消费者
 */
public class KConsumer extends Thread {

    private String topic;

    private KafkaConsumer<Integer, String> consumer;

    public KConsumer(String topic) {
        this.topic = topic;

        Properties properties = new Properties();
        properties.put("bootstrap.servers", KafkaProperties.BROKER_LIST);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", KafkaProperties.GROUP_ID);

        consumer = new KafkaConsumer<Integer, String>(properties);


    }

    @Override
    public void run() {
        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(100);
            for (ConsumerRecord<Integer, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                System.out.println();
            }
        }
    }
}
