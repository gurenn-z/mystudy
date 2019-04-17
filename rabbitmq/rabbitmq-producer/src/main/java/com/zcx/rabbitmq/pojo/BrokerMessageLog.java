package com.zcx.rabbitmq.pojo;

import java.util.Date;

/**
 * @author zoucaoxin
 * @date 2019/4/16
 * @description 订单消息的日志记录
 */
public class BrokerMessageLog {

    private String messageId;

    /**
     * Order对象进行json格式化后的内容
     */
    private String message;

    /**
     * message重发次数
     */
    private Integer tryCount = 0;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 下一次重发的时间
     */
    private Date nextRetry;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
