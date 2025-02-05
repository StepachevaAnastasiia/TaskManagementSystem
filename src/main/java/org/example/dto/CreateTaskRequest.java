package org.example.dto;

import lombok.Data;
import org.example.entity.Priority;
import org.example.entity.Status;

@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private Priority priority;
}
