package com.irina.task_manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository repository,
                       ProjectRepository projectRepository,
                       UserRepository userRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAll() {
        return repository.findAll().stream().map(TaskResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> search(TaskStatus status, Long projectId, Pageable pageable) {
        Page<Task> page;
        if (status != null) {
            page = repository.findByStatus(status, pageable);
        } else if (projectId != null) {
            page = repository.findByProjectId(projectId, pageable);
        } else {
            page = repository.findAll(pageable);
        }
        return page.map(TaskResponse::from);
    }

    @Transactional(readOnly = true)
    public TaskResponse getById(Long id) {
        return TaskResponse.from(find(id));
    }

    @Transactional
    public TaskResponse create(TaskRequest request) {
        Task task = new Task();
        apply(task, request);
        return TaskResponse.from(repository.save(task));
    }

    @Transactional
    public TaskResponse update(Long id, TaskRequest request) {
        Task task = find(id);
        apply(task, request);
        return TaskResponse.from(task);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Task find(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    private void apply(Task task, TaskRequest request) {
        task.setTitle(request.title());
        task.setDescription(request.description());
        if (request.status() != null) {
            task.setStatus(request.status());
        }
        if (request.projectId() != null) {
            task.setProject(projectRepository.findById(request.projectId())
                    .orElseThrow(() -> new TaskNotFoundException(request.projectId())));
        }
        if (request.assigneeId() != null) {
            task.setAssignee(userRepository.findById(request.assigneeId())
                    .orElseThrow(() -> new TaskNotFoundException(request.assigneeId())));
        }
    }
}