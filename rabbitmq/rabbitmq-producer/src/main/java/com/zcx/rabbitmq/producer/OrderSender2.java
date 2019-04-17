package com.zcx.rabbitmq.producer;

import com.zcx.rabbitmq.config.Constants;
import com.zcx.rabbitmq.mapper.BrokerMessageLogMapper;
import com.zcx.rabbitmq.pojo.Order;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zoucaoxin
 * @date 2019/4/17
 * @description 消息的可靠性投递实现
 */
@Component
public class OrderSender2 {

    /**
     * 自动注入RabbitTemplate模板
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 回调函数：confirm确认机制
     * 当消息发送后，broker会对消息有一个应答，此时就调用ConfirmCallback去监听应答的结果：ack（成功或失败）
     */
    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        /**
         * 消息发送到Broker后触发回调
         * @param correlationData 消息的唯一id
         * @param ack 应答结果  true:成功  false:失败
         * @param cause
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData：" + correlationData);
            String messageId = correlationData.getId();
            System.err.println("ack: " + ack);
            System.out.println(rabbitTemplate.getExchange());
            System.out.println(rabbitTemplate.getRoutingKey());
            System.out.println(rabbitTemplate.getUUID());
            if (ack) {
                // 如果返回成功，则更新订单的状态
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS, new Date());
            } else {
                // 如果返回失败：根据具体失败原因选择重试或补偿等手段
                System.err.println("异常处理......" + cause);
                // TODO: 2019/4/17 根据具体失败原因进行后续操作：选择重试或补偿等手段
            }
        }
    };

    /**
     * 发送消息方法调用: 构建自定义对象消息
     * @param order 订单实体
     */
    public synchronized void sendOrder(Order order) throws Exception {
        // 消息不是直接发送出去的，而是通过监听回调函数，实现ConfirmCallback接口，获取broker的应答结果
        rabbitTemplate.setConfirmCallback(confirmCallback);
        // 指定消息的唯一id
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange2", "order.rb2", order, correlationData);
    }
}