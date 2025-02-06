package org.example.controller;

import org.example.dto.*;
import org.example.entity.*;
import org.example.service.PersonService;
import org.example.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<TaskResponse> getTasks() {
        return map(taskService.allTasks());
    }

    @GetMapping("/api/tasks/{id}")
    public TaskResponse getTaskById(@PathVariable long id) {
        Task task = taskService.getTaskById(id);
        Person author = personService.getPerson(task.getAuthorId());
        Person assignee = personService.getPerson(task.getAssigneeId());
        return map(task, author, assignee);
    }

    @GetMapping("/api/tasks")
    public List<TaskResponse> getTasksByAuthor(@RequestParam long authorId) {
        return map(taskService.getTaskByAuthor(authorId));
    }

    @GetMapping("/api/tasks")
    public List<TaskResponse> getTasksByAssignee(@RequestParam long assigneeId) {
        return map(taskService.getTaskByAssignee(assigneeId));
    }

    @PutMapping("/api/tasks/{taskId}/status")
    public TaskResponse setStatus(@PathVariable long taskId, @RequestBody UpdateStatusRequest newStatus) {
        Task task = taskService.getTaskById(taskId);
        task = taskService.setStatus(task, newStatus);
        Person author = personService.getPerson(task.getAuthorId());
        Person assignee = personService.getPerson(task.getAssigneeId());
        return map(task, author, assignee);
    }

    @PutMapping("/api/tasks/{taskId}/priority")
    public TaskResponse setPriority(@PathVariable long taskId, @RequestBody UpdatePriorityRequest newPriority) {
        Task task = taskService.getTaskById(taskId);
        task = taskService.setPriority(task, newPriority);
        Person author = personService.getPerson(task.getAuthorId());
        Person assignee = personService.getPerson(task.getAssigneeId());
        return map(task, author, assignee);
    }

    @PutMapping("/api/tasks/{taskId}/assignee")
    public TaskResponse setAssignee(@PathVariable long taskId, @RequestBody UserInfo newAssignee) {
        Task task = taskService.getTaskById(taskId);
        task = taskService.setAssignee(task, map(newAssignee));
        Person author = personService.getPerson(task.getAuthorId());
        Person assignee = personService.getPerson(task.getAssigneeId());
        return map(task, author, assignee);
    }

    @DeleteMapping("/api/tasks/{taskId}")
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/api/tasks/{taskId}/comment")
    public CommentResponse addComment(@PathVariable long id, @RequestBody CreateCommentRequest comment) {
        Comment createdComment = taskService.addComment(comment, getCurrentUserId(), id);
        Person author = personService.getPerson(createdComment.getAuthorId());
        return map(createdComment, author);
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

    private List<TaskResponse> map(List<Task> tasks) {
        List<TaskResponse> taskResponseList = new ArrayList<>();
        for (Task task : tasks) {
            taskResponseList.add(
                    map(
                            task,
                            personService.getPerson(task.getAuthorId()),
                            personService.getPerson(task.getAssigneeId()))
            );
        }
        return taskResponseList;
    }

    private UserInfo map(Person person) {
        if (person == null) {
            return null;
        }

        return new UserInfo(person.getId(), person.getEmail());
    }

    private Person map(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }

        return new Person(userInfo.getId(), userInfo.getEmail());
    }

    private CommentResponse map(Comment comment, Person author) {
        return new CommentResponse(
                comment.getId(),
                comment.getText(),
                map(author),
                comment.getTaskId()
        );
    }

    private long getCurrentUserId() {
        return 1;
    }
}
