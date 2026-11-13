package com.irina.task_manager;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        Long projectId,
        Long assigneeId
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(), task.getTitle(), task.getDescription(),
                task.getStatus(), task.getCreatedAt(),
                task.getProject() != null ? task.getProject().getId() : null,
                task.getAssignee() != null ? task.getAssignee().getId() : null);
    }
}