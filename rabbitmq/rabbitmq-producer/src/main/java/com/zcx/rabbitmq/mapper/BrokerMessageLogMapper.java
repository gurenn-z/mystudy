package com.zcx.rabbitmq.mapper;

import com.sun.tools.javac.util.List;
import com.zcx.rabbitmq.pojo.BrokerMessageLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author zoucaoxin
 * @date 2019/4/16
 * @description
 */
@Repository
public interface BrokerMessageLogMapper {

    int insert(BrokerMessageLog record);

    int deleteByPrimaryKey(String messageId);

    int insertSelective(BrokerMessageLog record);

    BrokerMessageLog selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(BrokerMessageLog record);

    int updateByPrimaryKey(BrokerMessageLog record);

    /**
     * 查询消息状态为0(发送中) 且已经超时的消息集合
     */
    List<BrokerMessageLog> query4StatusAndTimeoutMessage();

    /**
     * 重新发送统计count发送次数 +1
     */
    void update4ReSend(@Param("messageId") String messageId, @Param("updateTime") Date updateTime);

    /**
     * 更新最终消息发送结果 成功 or 失败
     */
    void changeBrokerMessageLogStatus(@Param("messageId") String messageId, @Param("status") String status, @Param("updateTime") Date updateTime);


}
