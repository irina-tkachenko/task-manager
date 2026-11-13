package com.irina.task_manager;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank @Size(max = 255) String title,
        String description,
        TaskStatus status,
        Long projectId,
        Long assigneeId
) {}