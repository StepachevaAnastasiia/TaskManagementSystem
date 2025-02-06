package org.example.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CommentResponse {
    private final Long id;
    private final String text;
    private final UserInfo authorId;
    private final long taskId;
}
