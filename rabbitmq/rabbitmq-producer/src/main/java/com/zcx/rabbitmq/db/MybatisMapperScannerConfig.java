package com.zcx.rabbitmq.db;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zoucaoxin
 * @date 2019/4/16
 * @description 扫描mybatis下的dao对象（mappers）
 */
@Configuration
@AutoConfigureAfter(MybatisDataSourceConfig.class) // 在 MybatisDataSourceConfig 加载完后才加载本类
public class MybatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.zcx.rabbitmq.mapper");
        return mapperScannerConfigurer;
    }

}
