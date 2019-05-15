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

    // OrderSend2 ---- 的场景
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
//                    durable = "${spring.rabbitmq.listener.order.queue.durable}"),
//            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
//                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
//                    type = "${spring.rabbitmq.listener.order.exchange.type}",
//                    ignoreDeclarationExceptions = "spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions"),
//            key = "${spring.rabbitmq.listener.order.key}")
//    )
    // OrderSend ---- 的场景
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue2", durable = "true"),
            exchange = @Exchange(value = "order-exchange2", durable = "true", type = "topic"),
            key = "order.#")
    )
    @RabbitHandler
    public void onOrderReceiver(@Payload Order order, @Headers Map<String, Object> headers,
                                Channel channel) throws Exception {
        // 消费者操作
        System.err.println("--------------收到消息，开始消费--------------");
        System.err.println("订单ID：" + order.getId());
        System.err.println(order.toString());
        // 手工签收模式下，必须手动确认签收，告诉mq消息已处理完
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        System.err.println(deliveryTag);
        // deliveryTag: 唯一标识 ID。当一个消费者向 RabbitMQ 注册后，会建立起一个 Channel，RabbitMQ
        // 会用 basic.deliver方法向消费者推送消息，这个方法携带了一个 delivery tag，
        // 它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，
        // 是一个单调递增的正整数，delivery tag 的范围仅限于 Channel
        // multiple：为了减少网络流量，手动确认可以被批处理，
        // 当该参数为 true 时，则可以一次性确认 delivery_tag ≤ 传入值的所有消息是否批量签收.
        // true:将一次性消费ack所有小于deliveryTag的消息。
        // false : 非批量签收
        channel.basicAck(deliveryTag, false);

    }
}
