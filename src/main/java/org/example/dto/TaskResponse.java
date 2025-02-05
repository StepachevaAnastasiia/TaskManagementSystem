package org.example.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.example.entity.Person;
import org.example.entity.Priority;
import org.example.entity.Status;

@Data
public class TaskResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final Status status;
    private final Priority priority;
    private final UserInfo authorId;
    private final UserInfo assigneeId;
}
