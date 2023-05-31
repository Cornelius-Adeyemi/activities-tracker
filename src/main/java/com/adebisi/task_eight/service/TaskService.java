package com.adebisi.task_eight.service;

import com.adebisi.task_eight.DTO.TaskDTO;
import com.adebisi.task_eight.enumpackage.Status;
import com.adebisi.task_eight.model.Task;
import com.adebisi.task_eight.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

    Task findTaskById(Long id);

    Task saveTask(Task task);

    void deleteTaskById(Long id);

    List<Task> findAllbyId(Long id);

    Page<Task> findAllTaskByIdAndSortedByDATE(Long id, int pageNo, int pageSize);

    Page<Task> findTaskByUserAndStatusSortedBYDate(Long id, Status status, int pageNo, int pageSize );

    User addtask(TaskDTO taskDTO, Long id);

    Task editTask(TaskDTO taskDTO);

    Task moveTaskForward(Long taskId);

    Task moveTaskBackward(Long taskId);
}
