package org.example.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserInfo {
    private Long id;
    private String email;

    public UserInfo(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
