package com.adebisi.task_eight.model;


import com.adebisi.task_eight.DTO.TaskDTO;
import com.adebisi.task_eight.enumpackage.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private Status status = Status.PENDING;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

    private LocalDateTime completed;
    @CreationTimestamp
    private LocalDateTime createddate;
    @UpdateTimestamp
    private LocalDateTime lastedited;


    public Task(TaskDTO taskDTO){
        title = taskDTO.getTitle();
        description = taskDTO.getDescription();

    }
}
