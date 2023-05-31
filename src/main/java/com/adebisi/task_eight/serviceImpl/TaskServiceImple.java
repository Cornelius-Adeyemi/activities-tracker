package com.adebisi.task_eight.serviceImpl;


import com.adebisi.task_eight.DTO.TaskDTO;
import com.adebisi.task_eight.enumpackage.Status;
import com.adebisi.task_eight.model.Task;
import com.adebisi.task_eight.model.User;
import com.adebisi.task_eight.repo.TaskRepo;
import com.adebisi.task_eight.service.TaskService;
import com.adebisi.task_eight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImple  implements TaskService {
    UserService userService;
    TaskRepo taskRepo;
@Autowired
    public TaskServiceImple(TaskRepo taskRepo,UserService userService){
    this.userService= userService;
    this.taskRepo = taskRepo;
    }







    @Override
    public Task findTaskById(Long id) {
        Task task = taskRepo.findById(id).get();
        return task;
    }

    @Override
    public Task saveTask(Task task) {
        Task task1 = taskRepo.save(task);
        return task1;
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepo.deleteById(id);
    }

    public List<Task> findAllbyId(Long id){
        return taskRepo.findTasksByUser_Id(id);
    }

    public Page<Task> findAllTaskByIdAndSortedByDATE(Long id, int pageNo, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNo-1, pageSize);
      Page<Task> page = taskRepo.findTasksByUser_IdOrderByLasteditedDesc(id, pageRequest);
      return page;
    }


    public Page<Task> findTaskByUserAndStatusSortedBYDate(Long id, Status status, int pageNo, int pageSize ){
    PageRequest pageRequest =PageRequest.of(pageNo-1,pageSize);
    Page<Task> page = taskRepo.findTasksByUser_IdAndStatusOrderByLasteditedDesc(id,status,pageRequest);
    return page;
    }


    public User addtask(TaskDTO taskDTO, Long id){
        Task task = new Task(taskDTO);
        User user = userService.findUserById(id);
        task.setUser(user);
        user.getTaskList().add(task);

      return   userService.saveUser(user);
    }


    public Task editTask(TaskDTO taskDTO){

        Task task = findTaskById(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
         return   saveTask(task);
    }


    public Task moveTaskForward(Long taskId){
        Task task = findTaskById(taskId);

        if(task.getStatus()==Status.PENDING){
            task.setStatus(Status.IN_PROGRESS);
            task.setCompleted(null);
        }else if(task.getStatus() ==Status.IN_PROGRESS){
            task.setStatus(Status.COMPLETED);
            task.setCompleted(LocalDateTime.now());
        }
         return saveTask(task);
    }

    public Task moveTaskBackward(Long taskId){
        Task task = findTaskById(taskId);

        if(task.getStatus()==Status.COMPLETED){
            task.setStatus(Status.IN_PROGRESS);
            task.setCompleted(null);
        }else if(task.getStatus() ==Status.IN_PROGRESS){
            task.setStatus(Status.PENDING);
            task.setCompleted(null);
        }
         return saveTask(task);
    }
}
