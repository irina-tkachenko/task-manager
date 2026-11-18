package com.irina.task_manager;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public TaskEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishStatusChanged(Long taskId, TaskStatus from, TaskStatus to) {
        String message = "Task " + taskId + " status changed: " + from + " -> " + to;
        rabbitTemplate.convertAndSend(RabbitConfig.TASK_STATUS_QUEUE, message);
    }
}