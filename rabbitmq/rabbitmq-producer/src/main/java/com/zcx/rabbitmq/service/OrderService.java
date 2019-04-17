package com.zcx.rabbitmq.service;

import com.zcx.rabbitmq.config.Constants;
import com.zcx.rabbitmq.mapper.BrokerMessageLogMapper;
import com.zcx.rabbitmq.mapper.OrderMapper;
import com.zcx.rabbitmq.pojo.BrokerMessageLog;
import com.zcx.rabbitmq.pojo.Order;
import com.zcx.rabbitmq.producer.OrderSender2;
import com.zcx.rabbitmq.utils.FastJsonConvertUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zoucaoxin
 * @date 2019/4/17
 * @description
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private OrderSender2 orderSender2;

    /**
     * 创建一个订单
     * @param order
     */
    public void createOrder(Order order) throws Exception {
        // 使用当前时间当做订单创建时间（为了模拟一下简化）
        Date orderTime = new Date();
        // 业务消息入库
        orderMapper.insert(order);

        // 创建消息的日志记录对象
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        // 消息id
        brokerMessageLog.setMessageId(order.getMessageId());
        // 将订单消息对象order转化为json格式存储
        brokerMessageLog.setMessage(FastJsonConvertUtil.convertObjectToJSON(order));
        // 设置订单的发送状态为0 表示发送中
        brokerMessageLog.setStatus("0");
        // 设置消息的超时时间订单创建时间+自定义的超时时间（1min）
        // 当消息创建1min后，如果还没有进行消息状态的更新，则会从定时任务中抓取该消息并重发
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constants.ORDER_TIMEOUT));
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        // 日志记录消息入库
        brokerMessageLogMapper.insertSelective(brokerMessageLog);

        // 发送消息
        orderSender2.sendOrder(order);

    }
}
