package com.zcx.rabbitmq.task;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author zoucaoxin
 * @date 2019/4/16
 * @description 定时任务
 */
@Configuration
@EnableScheduling // 启动定时任务
public class TaskSchedulerConfig implements SchedulingConfigurer {

    /**
     * 设置线程池
     * @param taskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());
    }

    /**
     * 线程池任务
     * 指定100个线程
     */
    @Bean(destroyMethod = "shutdown")
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(100);
    }

}
