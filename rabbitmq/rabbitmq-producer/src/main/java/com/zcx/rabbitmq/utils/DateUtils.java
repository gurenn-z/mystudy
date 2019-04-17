package com.zcx.rabbitmq.utils;

import java.util.Date;

/**
 * @author zoucaoxin
 * @date 2019/4/17
 * @description
 */
public class DateUtils {

    public static Date addMinutes(Date orderTime, int orderTimeout) {
        Date afterDate = new Date(orderTime.getTime() + 60000 * orderTimeout);
        return afterDate;
    }
}
