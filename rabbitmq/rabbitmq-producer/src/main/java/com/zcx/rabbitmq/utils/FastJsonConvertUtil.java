package com.zcx.rabbitmq.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author zoucaoxin
 * @date 2019/4/17
 * @description json字符串与对象的相互转换工具类
 */
public class FastJsonConvertUtil {

    /**
     * 将Json字符串转换成对象
     * @param data Json字符串
     * @param clazz 转换对象
     * @return 对象
     */
    public static <T> T convertJSONToObject(String data, Class<T> clazz) {
        try {
            T t = JSON.parseObject(data, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象转换成Json格式字符串
     * @param obj 任意对象
     * @return json字符串
     */
    public static String convertObjectToJSON(Object obj) {
        try {
            String text = JSON.toJSONString(obj);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
