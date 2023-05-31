package com.adebisi.task_eight.controller;


import com.adebisi.task_eight.DTO.TaskDTO;
import com.adebisi.task_eight.enumpackage.Status;
import com.adebisi.task_eight.model.Task;
import com.adebisi.task_eight.model.User;
import com.adebisi.task_eight.others.Others;
import com.adebisi.task_eight.service.TaskService;
import com.adebisi.task_eight.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TaskController {

    UserService userService;
    TaskService taskService;

    public TaskController(UserService userService, TaskService taskService){
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String homepage(HttpServletRequest request, Model model){
        Long id = Others.checkIfLogin(request);
        String status = request.getParameter("status");
        String pageNo = request.getParameter("pageNo") ;


        if(id ==null){
            return "redirect:/user/login";
        }
        int defaultpage =1;
        int pageSize =5;

        Page<Task> tasks=null;
        if(pageNo !=null){
            defaultpage = Integer.parseInt(pageNo);
        }

        List<Task> taskList;
        User user = userService.findUserById(id);
        if(status == null || status.equals("")) {
            tasks = taskService.findAllTaskByIdAndSortedByDATE(id, defaultpage,pageSize);
           // taskList = user.getTaskList();


        }else if(status.equalsIgnoreCase("pending")){
            tasks = taskService.findTaskByUserAndStatusSortedBYDate(id,Status.PENDING,defaultpage,pageSize );
          //taskList =  user.getTaskList().stream().filter(e->e.getStatus().equals(Status.PENDING)).toList();
        }else if(status.equalsIgnoreCase("progress")){
            tasks = taskService.findTaskByUserAndStatusSortedBYDate(id,Status.IN_PROGRESS,defaultpage,pageSize );
          //  taskList =  user.getTaskList().stream().filter(e->e.getStatus().equals(Status.IN_PROGRESS)).toList();
        }else{
            tasks = taskService.findTaskByUserAndStatusSortedBYDate(id,Status.COMPLETED,defaultpage,pageSize );
         //   taskList =  user.getTaskList().stream().filter(e->e.getStatus().equals(Status.COMPLETED)).toList();
        }

        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("name");

        model.addAttribute("tasks" ,tasks.getContent());
        model.addAttribute("totalTasks", tasks.getTotalElements());
        model.addAttribute("currentPage", defaultpage);
        model.addAttribute("totalPage",tasks.getTotalPages());
        model.addAttribute("status", status);
        model.addAttribute("name", name);


        return "index";
    }


    @GetMapping("/pending")
    public String pendingtasks(){
        return "redirect:/?status=pending";
    }

    @GetMapping("/progress")
    public String progresstasks(){
        return "redirect:/?status=progress";
    }

    @GetMapping("/complete")
    public String completedtasks(){
        return "redirect:/?status=completed";
    }


    @GetMapping("/addtask")
    public String addtaskpage(HttpServletRequest request, Model model ){
       Long id = Others.checkIfLogin(request);
        if(id ==null){
            return "redirect:/user/login";
        }

        TaskDTO taskDTO = new TaskDTO();
        model.addAttribute("task", taskDTO);
       return "addtask";
    }


    @PostMapping("/addtask")
    public String addTask(@ModelAttribute("task") TaskDTO taskDTO, HttpServletRequest request, Model model){

        if(taskDTO.getTitle().length()<5){
            model.addAttribute("error", "Title should be greater than 5 characters");
            model.addAttribute("task", taskDTO);
            return "addtask";
        }

        if(taskDTO.getDescription().length()<=10){
            model.addAttribute("error", "Description shouldn't be less than 10 character");
            model.addAttribute("task", taskDTO);
            return "addtask";
        }

       Long id = Others.checkIfLogin(request);


           taskService.addtask(taskDTO,  id);

        return "redirect:/";

    }

    @GetMapping("/edit/{taskId}")
    public String editPage(@PathVariable("taskId") Long taskId, HttpServletRequest request, Model model){
    Long id = Others.checkIfLogin(request);
    if(id==null){
        return "redirect:/user/login";
    }
    String status = request.getParameter("status");

    Task task  = taskService.findTaskById(taskId);
    TaskDTO taskDTO = new TaskDTO(task);
    model.addAttribute("task", taskDTO);
    model.addAttribute("status",status);
     return "edittask";
    }

    @PostMapping("/edit")
    public String editSubmit(@ModelAttribute("task") TaskDTO taskDTO, HttpServletRequest request){
        taskService.editTask( taskDTO);

        String status = request.getParameter("status");
        System.out.println("*****************postmapping "+ status);

        if(status ==null || status.equals("")){
            return "redirect:/";
        }



        return "redirect:/?status="+status;


    }



    @GetMapping("/move/{taskId}")
    public String moveforward(@PathVariable("taskId") Long taskId,HttpServletRequest request, Model model){

        taskService.moveTaskForward(taskId);

     return "redirect:/";
    }

    @GetMapping("/moveback/{taskId}")
    public String moveback(@PathVariable("taskId") Long taskId){
        taskService.moveTaskBackward( taskId);

        return "redirect:/";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long taskId){
        taskService.deleteTaskById(taskId);
        return "redirect:/";
    }

   @GetMapping("/view/{taskId}")
   public String viewpage(@PathVariable("taskId") Long taskId,HttpServletRequest request, Model model){
       Long id = Others.checkIfLogin(request);
       if(id==null){
           return "redirect:/";
       }
        Task task = taskService.findTaskById(taskId);
        String status = request.getParameter("status");
        model.addAttribute("task", task);
        model.addAttribute("status", status);

        return "viewtask";


   }

   @GetMapping("/view/back")
   public String  viewBack(HttpServletRequest request){

       String status = request.getParameter("status");
       System.out.println("*****************postmapping "+ status);

       if(status ==null || status.equals("")){
           return "redirect:/";
       }

       return "redirect:/?status="+status;

   }

   @GetMapping("/test")
   public String testing(HttpServletRequest request){
        Long id = Others.checkIfLogin(request);

        Page<Task> page = taskService.findTaskByUserAndStatusSortedBYDate(id, Status.COMPLETED, 1,4);
       System.out.println("***************** test" + page.getContent());
       System.out.println("***************** length" + page.getTotalElements());
       System.out.println("***************** pages" + page.getTotalPages());


        return "test";
   }

    @GetMapping("/paging/{id}")
    public String page(@PathVariable("id") int id, HttpServletRequest request){
        String status= request.getParameter("status");
        if( !status.equals("")){
            return "redirect:/?status="+status+"&"+"pageNo="+id;
           // return "redirect:/?status="+status+"&"+"pageNo="+id;
        }


        return "redirect:/?pageNo=" + id;


    }

}
