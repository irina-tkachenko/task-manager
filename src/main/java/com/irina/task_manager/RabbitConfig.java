package com.irina.task_manager;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String TASK_STATUS_QUEUE = "task-status-queue";

    @Bean
    public Queue taskStatusQueue() {
        return new Queue(TASK_STATUS_QUEUE, true);
    }
}
