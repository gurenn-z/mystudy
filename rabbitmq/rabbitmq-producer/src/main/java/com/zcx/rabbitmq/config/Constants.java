package com.zcx.rabbitmq.config;

/**
 * @author zoucaoxin
 * @date 2019/4/17
 * @description 自定义常量
 */
public class Constants {

    /**
     * 订单消息状态：发送中
     */
    public static final String ORDER_SENDING = "0";

    /**
     * 订单消息状态：发送成功
     */
    public static final String ORDER_SEND_SUCCESS = "1";

    /**
     * 订单消息状态：发送失败
     */
    public static final String ORDER_SEND_FAILURE = "2";

    /**
     * 消息超时时间：1min（分钟超时单位：min)
     * 当消息入库后，经过1min，如果还没有更改订单消息的状态，比如，从0到1，则认为该消息是过期的，定时任务就会抓取出来
     */
    public static final int ORDER_TIMEOUT = 1;
}
