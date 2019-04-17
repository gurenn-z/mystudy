package com.zcx.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.zcx.rabbitmq.pojo.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zoucaoxin
 * @date 2019/4/16
 * @description 消息接收
 */
@Component
public class OrderReceiver {
    // OrderSend ---- 的场景
    //    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "order-queue", durable = "true"),
//            exchange = @Exchange(value = "order-exchange", durable = "true", type = "topic"),
//            key = "order.#")
//    )

    // OrderSend2 ---- 的场景
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                    durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                    type = "${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions"),
            key = "${spring.rabbitmq.listener.order.key}")
    )
    @RabbitHandler
    public void onOrderReceiver(@Payload Order order, @Headers Map<String, Object> headers,
                                Channel channel) throws Exception {
        // 消费者操作
        System.err.println("--------------收到消息，开始消费--------------");
        System.err.println("订单ID：" + order.getId());

        // 手工签收模式下，必须手动确认签收，告诉mq消息已处理完
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // false: 不支持批量签收
        channel.basicAck(deliveryTag, false);
    }
}
