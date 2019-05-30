package com.zcx.sparktrain.kafka;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author zoucaoxin
 * @date 2019/5/30
 * @description kafka 生产者
 */
public class KProducer extends Thread {

    private String topic;

    private Producer<Integer, String> producer;

    public KProducer(String topic) {
        this.topic = topic;

        Properties properties = new Properties();
        properties.put("bootstrap.servers", KafkaProperties.BROKER_LIST);
        properties.put("bootstrap.servers", KafkaProperties.BROKER_LIST);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks", "all");

        producer = new KafkaProducer<Integer, String>(properties);
    }

    @Override
    public void run() {
        int messageNum = 1;
        while (true) {
            String message = "message_" + messageNum;
            producer.send(new ProducerRecord<Integer, String>(topic, messageNum, message));
            System.out.println("Send: " + message);

            messageNum ++;

            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
