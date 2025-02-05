package org.example.dto;

import lombok.Data;
import org.example.entity.Status;
@Data
public class UpdateStatusRequest {
    private final Status status;
}
