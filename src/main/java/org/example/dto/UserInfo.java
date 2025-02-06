package org.example.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserInfo {
    private final Long id;
    private final String email;

}
