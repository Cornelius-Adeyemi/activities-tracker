package com.adebisi.task_eight.repo;

import com.adebisi.task_eight.DTO.TaskDTO;
import com.adebisi.task_eight.enumpackage.Status;
import com.adebisi.task_eight.model.Task;
import com.adebisi.task_eight.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepoTest {
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    UserRepo userRepo;

    @Test
    public void saveUser(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("we are testing");
        taskDTO.setDescription("hshfdh iejr rhfh ijjf j f");

        Task task = new Task(taskDTO);

        User user = new User();
        user.setName("Adebisi Adeyemi");
        user.setPassword("yetunde");
        user.setEmail("aadebisi11@yahoo.com");

        task.setUser(user);
        user.getTaskList().add(task);

        userRepo.save(user);

    }
    @Test
    public void getTask(){
        System.out.println(Status.PENDING==Status.PENDING);
    }

}