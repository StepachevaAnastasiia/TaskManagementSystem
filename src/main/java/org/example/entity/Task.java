package org.example.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    private String description;
    //@Enumerated(EnumType.STRING)
    private Status status;
    //@Enumerated(EnumType.STRING)
    private Priority priority;
    private long authorId;
    private Long assigneeId;

}
