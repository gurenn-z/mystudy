package com.zcx.rabbitmq.pojo;

import java.io.Serializable;

/**
 * @author zoucaoxin
 * @date 2019/4/16
 * @description 订单消息
 */
public class Order implements Serializable {

    private static final long serialVersionUID = -7318269242977089336L;

    /**
     * 订单id
     */
    private String id;

    /**
     * 订单名称
     */
    private String name;

    /**
     * 存储消息发送的唯一标识
     */
    private String messageId;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
