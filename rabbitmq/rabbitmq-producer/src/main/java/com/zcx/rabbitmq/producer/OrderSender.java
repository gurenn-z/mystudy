package com.zcx.rabbitmq.producer;

import com.zcx.rabbitmq.pojo.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zoucaoxin
 * @date 2019/4/16
 * @description RabbitMQ整合SpringBoot
 */
@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rt;

    public void sendOrder(Order order) throws Exception {

        // 设置消息id
        CorrelationData cd = new CorrelationData();
        cd.setId(order.getMessageId());

        rt.convertAndSend("order-exchange", // exchange
                "order.rabbit", // routing key
                order, // 实体信息（消息体内容）
                cd); // 唯一消息id
    }

}
