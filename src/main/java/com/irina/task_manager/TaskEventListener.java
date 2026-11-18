package com.irina.task_manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TaskEventListener {

    private static final Logger log = LoggerFactory.getLogger(TaskEventListener.class);

    @RabbitListener(queues = RabbitConfig.TASK_STATUS_QUEUE)
    public void onStatusChanged(String message) {
        log.info("Notification received: {}", message);
    }
}