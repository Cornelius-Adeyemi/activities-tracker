package com.adebisi.task_eight.serviceImpl;

import com.adebisi.task_eight.DTO.TaskDTO;
import com.adebisi.task_eight.enumpackage.Status;
import com.adebisi.task_eight.model.Task;
import com.adebisi.task_eight.model.User;
import com.adebisi.task_eight.repo.TaskRepo;
import com.adebisi.task_eight.repo.UserRepo;
import com.adebisi.task_eight.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceImpleTest {
 @MockBean
    TaskRepo taskRepo;
   @MockBean
   UserRepo userRepo;

   @Autowired
   TaskService taskService;

    @BeforeEach
    void setUp() {

        User user = new User();
        user.setId(1L);
        user.setName("Adebisi Adeyemi");
        user.setPassword("aadebisi11@yahoo.com");

        Task task1 = new Task();
        task1.setTask_id(1L);
        task1.setTitle("Dancing Class");
        task1.setDescription("Dancing is another form exercise");
        task1.setUser(user);

        Task task2 = new Task();
        task2.setTask_id(2L);
        task2.setTitle("fight Class");
        task2.setDescription("fighting is another form exercise");
        task2.setUser(user);

        user.setTaskList(new ArrayList<>(List.of(task1,task2)));

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepo.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepo.findById(2L)).thenReturn(Optional.of(task2));
        when(userRepo.save(any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object arg = invocationOnMock.getArgument(0);
                return arg;
            }
        });

        when(taskRepo.save(any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object arg = invocationOnMock.getArgument(0);
                return arg;
            }
        });




    }

    @Test
    void findTaskById() {
        Task task = taskService.findTaskById(1L);
        assertEquals(Status.PENDING, task.getStatus());

    }
@Test
    void addtask(){
    TaskDTO taskDTO = new TaskDTO();
    taskDTO.setTitle("we go from here");
    taskDTO.setDescription("we have done our best");
    User user = taskService.addtask(taskDTO, 1L);
    assertEquals(3, user.getTaskList().size());
    }

    @Test
    void editTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitle("we go from here");
        taskDTO.setDescription("we have done our best");
        Task task = taskService.editTask(taskDTO);
        assertEquals("we go from here", task.getTitle());

    }

    @Test
    void moveTaskForward() {
        Task task = taskService.moveTaskForward(1L);
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void moveTaskBackward() {
        Task task = taskService.moveTaskBackward(1L);
        assertEquals(Status.PENDING, task.getStatus());
    }
}