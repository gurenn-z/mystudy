package com.zcx.rabbitmq.mapper;

import com.zcx.rabbitmq.pojo.Order;
import org.springframework.stereotype.Repository;

/**
 * @author zoucaoxin
 * @date 2019/4/16
 * @description
 */
@Repository
public interface OrderMapper {

    int insert(Order record);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

}
