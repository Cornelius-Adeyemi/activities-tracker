package com.adebisi.task_eight.model;

import com.adebisi.task_eight.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(
        name="user_email",
        columnNames = "email"
)})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Task> taskList = new ArrayList<>();


    public User(UserDTO userDTO){

        name = userDTO.getName();
        email= userDTO.getEmail();
        password = userDTO.getPassword();
    }

}
