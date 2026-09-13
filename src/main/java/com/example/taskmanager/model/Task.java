package com.example.taskmanager.model;

import com.example.taskmanager.enums.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonBackReference("user-tasks")
    private User user;
}
