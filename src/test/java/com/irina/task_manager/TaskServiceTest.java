package com.irina.task_manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void getById_returnsTask_whenExists() {
        Task task = new Task();
        task.setTitle("Test");
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = service.getById(1L);

        assertEquals("Test", response.title());
        assertEquals(TaskStatus.TODO, response.status());
    }

    @Test
    void getById_throws_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void create_savesTaskWithGivenData() {
        TaskRequest request = new TaskRequest("New task", "Description", TaskStatus.IN_PROGRESS);
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        TaskResponse response = service.create(request);

        assertEquals("New task", response.title());
        assertEquals(TaskStatus.IN_PROGRESS, response.status());
        verify(repository).save(any(Task.class));
    }

    @Test
    void create_usesDefaultStatus_whenStatusIsNull() {
        TaskRequest request = new TaskRequest("Task", null, null);
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        TaskResponse response = service.create(request);

        assertEquals(TaskStatus.TODO, response.status());
    }

    @Test
    void getAll_returnsAllTasks() {
        Task t1 = new Task();
        t1.setTitle("A");
        Task t2 = new Task();
        t2.setTitle("B");
        when(repository.findAll()).thenReturn(List.of(t1, t2));

        List<TaskResponse> result = service.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void update_changesFields() {
        Task task = new Task();
        task.setTitle("Old");
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = service.update(1L, new TaskRequest("New", "Desc", TaskStatus.DONE));

        assertEquals("New", response.title());
        assertEquals(TaskStatus.DONE, response.status());
    }

    @Test
    void delete_throws_whenNotFound() {
        when(repository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> service.delete(5L));
        verify(repository, never()).delete(any());
    }

    @Test
    void delete_removesTask_whenExists() {
        Task task = new Task();
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        service.delete(1L);

        verify(repository).delete(task);
    }
}