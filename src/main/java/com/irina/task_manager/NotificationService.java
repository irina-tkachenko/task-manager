package com.irina.task_manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Async("notificationExecutor")
    public void send(String message) {
        log.info("[{}] Sending notification: {}", Thread.currentThread().getName(), message);
    }
}