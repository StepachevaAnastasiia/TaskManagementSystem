package org.example.service;

import org.example.dto.CreateCommentRequest;
import org.example.dto.CreateTaskRequest;
import org.example.dto.UpdatePriorityRequest;
import org.example.dto.UpdateStatusRequest;
import org.example.entity.Comment;
import org.example.entity.Person;
import org.example.entity.Status;
import org.example.entity.Task;
import org.example.repository.CommentRepository;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public TaskService(TaskRepository taskRepository, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

    public List<Task> allTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(long id) {
        return taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Task> getTaskByAuthor(long authorId) {
        return taskRepository.findByAuthorId(authorId);
    }

    public List<Task> getTaskByAssignee(long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    public Task setStatus(Task task, UpdateStatusRequest newStatus) {
        task.setStatus(newStatus.getStatus());
        taskRepository.save(task);
        return task;
    }

    public Task setPriority(Task task, UpdatePriorityRequest newPriority) {
        task.setPriority(newPriority.getPriority());
        taskRepository.save(task);
        return task;
    }

    public Task setAssignee(Task task, Person newAssignee) {
        task.setAssigneeId(newAssignee.getId());
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    public Task addTask(CreateTaskRequest task, long currentUserId) {
        return taskRepository.save(
                new Task(null,
                        task.getTitle(),
                        task.getDescription(),
                        Status.WAITING,
                        task.getPriority(),
                        currentUserId,
                        null));
    }

    public Comment addComment(CreateCommentRequest comment, long currentUserId, long taskId) {
        return commentRepository.save(new Comment(
                null,
                comment.getText(),
                currentUserId,
                taskId
        ));
    }
}
