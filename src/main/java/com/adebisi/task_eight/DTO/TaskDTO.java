package com.adebisi.task_eight.DTO;


import com.adebisi.task_eight.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;

    private String title;

    private String description;

    public TaskDTO(Task task){
        id= task.getTask_id();
        title = task.getTitle();
        description = task.getDescription();

    }


}
