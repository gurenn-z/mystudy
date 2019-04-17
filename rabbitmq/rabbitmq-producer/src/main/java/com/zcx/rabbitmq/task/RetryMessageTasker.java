package com.zcx.rabbitmq.task;

import com.zcx.rabbitmq.config.Constants;
import com.zcx.rabbitmq.mapper.BrokerMessageLogMapper;
import com.zcx.rabbitmq.pojo.BrokerMessageLog;
import com.zcx.rabbitmq.pojo.Order;
import com.zcx.rabbitmq.producer.OrderSender2;
import com.zcx.rabbitmq.utils.FastJsonConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author zoucaoxin
 * @date 2019/4/16
 * @description 消息重发
 */
@Component
public class RetryMessageTasker {


    @Autowired
    private OrderSender2 sender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 定时任务
     * 项目启动3s后，每隔10s去抽取一次
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void reSend(){
        System.err.println("-----------定时任务开始-----------");
        // 从日志表中抽取status=0和超时的消息
        List<BrokerMessageLog> list = brokerMessageLogMapper.query4StatusAndTimeoutMessage();

        list.forEach(messageLog -> {
            // 如果消息已经重发了3次以上
            if(messageLog.getTryCount() >= 3){
                // 更新消息的状态为失败
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLog.getMessageId(), Constants.ORDER_SEND_FAILURE, new Date());
            } else {
                // 否则，将重试次数+1，再次发送
                brokerMessageLogMapper.update4ReSend(messageLog.getMessageId(),  new Date());
                Order reSendOrder = FastJsonConvertUtil.convertJSONToObject(messageLog.getMessage(), Order.class);
                try {
                    sender.sendOrder(reSendOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("-----------异常处理-----------");
                    // TODO: 2019/4/17 具体的异常处理逻辑
                }
            }
        });
    }
}