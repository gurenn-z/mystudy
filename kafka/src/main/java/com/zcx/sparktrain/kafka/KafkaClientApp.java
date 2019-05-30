package com.zcx.sparktrain.kafka;

/**
 * @author zoucaoxin
 * @date 2019/5/30
 * @description Kafka 测试类
 */
public class KafkaClientApp {

    public static void main(String[] args) {

        new KProducer(KafkaProperties.TOPIC).start();

        new KConsumer(KafkaProperties.TOPIC).start();
    }

}
