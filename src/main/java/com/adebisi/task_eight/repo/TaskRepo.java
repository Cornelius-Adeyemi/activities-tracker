package com.adebisi.task_eight.repo;

import com.adebisi.task_eight.enumpackage.Status;
import com.adebisi.task_eight.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

   List<Task> findTasksByUser_Id(Long id);

  // Page<Task>  findTasksByUser_IdOrderByLast_editedAsc(Long id, Pageable pageable);

   Page<Task>  findTasksByUser_IdOrderByLasteditedDesc(Long id, Pageable pageable);

   Page<Task>  findTasksByUser_IdAndStatusOrderByLasteditedDesc(Long id, Status status, Pageable pageable);

}
