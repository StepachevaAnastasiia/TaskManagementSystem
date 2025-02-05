package org.example.dto;

import lombok.Data;
import org.example.entity.Priority;
@Data
public class UpdatePriorityRequest {
    private final Priority priority;
}
