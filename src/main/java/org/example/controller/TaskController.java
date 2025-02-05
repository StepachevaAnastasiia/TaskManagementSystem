package org.example.controller;

import org.example.dto.*;
import org.example.entity.*;
import org.example.service.PersonService;
import org.example.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;
    private final PersonService personService;

    public TaskController(TaskService taskService, PersonService personService) {
        this.taskService = taskService;
        this.personService = personService;
    }

    @GetMapping("/api/tasks")
    public List<Task> getTasks() {
        return taskService.allTasks();
    }

    @GetMapping("/api/tasks/{id}")
    public Task getTaskById(@PathVariable long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/api/tasks")
    public List<Task> getTasksByAuthor(@RequestParam long authorId) {
        return taskService.getTaskByAuthor(authorId);
    }

    @GetMapping("/api/tasks")
    public List<Task> getTasksByAssignee(@RequestParam long assigneeId) {
        return taskService.getTaskByAssignee(assigneeId);
    }


    @PutMapping("/api/tasks/{taskId}/status")
    public Task setStatus(@PathVariable long taskId, @RequestBody UpdateStatusRequest newStatus) {
        Task task = taskService.getTaskById(taskId);
        return taskService.setStatus(task, newStatus);
    }

    @PutMapping("/api/tasks/{taskId}/priority")
    public Task setPriority(@PathVariable long taskId, @RequestBody UpdatePriorityRequest newPriority) {
        Task task = taskService.getTaskById(taskId);
        return taskService.setPriority(task, newPriority);
    }

    @PutMapping("/api/tasks/{taskId}/assignee")
    public Task setAssignee(@PathVariable long taskId, @RequestBody Person newAssignee) {
        Task task = taskService.getTaskById(taskId);
        return taskService.setAssignee(task, newAssignee);
    }

    @DeleteMapping("/api/tasks/{taskId}")
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/api/tasks/{taskId}/comment")
    public Comment addComment(@PathVariable long id, @RequestBody Comment comment) {
        return taskService.addComment(comment);
    }

    @PostMapping("/api/tasks")
    public TaskResponse addTask(@RequestBody CreateTaskRequest task) {
        Task createdTask = taskService.addTask(task, getCurrentUserId());
        Person author = personService.getPerson(createdTask.getAuthorId());
        Person assignee = personService.getPerson(createdTask.getAssigneeId());

        return map(createdTask, author, assignee);
    }

    private TaskResponse map(
            Task task,
            Person author,
            Person assignee
    ) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                map(author),
                map(assignee)
        );
    }

    private UserInfo map(Person person) {
        if(person == null) {
            return null;
        }

        return new UserInfo(person.getId(), person.getEmail());
    }

    private long getCurrentUserId() {
        return 1;
    }
}
