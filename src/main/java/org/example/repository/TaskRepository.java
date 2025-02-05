package org.example.repository;

import org.example.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByAuthorId(long authorId);

    List<Task> findByAssigneeId(long assigneeId);
}
