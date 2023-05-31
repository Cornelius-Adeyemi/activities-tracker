package com.adebisi.task_eight.controller;


import com.adebisi.task_eight.DTO.UserDTO;
import com.adebisi.task_eight.model.User;
import com.adebisi.task_eight.others.Others;
import com.adebisi.task_eight.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

  UserService userService;

  @Autowired
  public  UserController(UserService userService){
      this.userService = userService;
  }

  @GetMapping("/login")
  public String loginpage( Model model, HttpServletRequest request){

      HttpSession session = request.getSession();
      Long id =(Long) session.getAttribute("userId");
      if(id != null){
          return "redirect:/";
      }

      UserDTO userDTO = new UserDTO();
      model.addAttribute("user", userDTO);
      String error = request.getParameter("error");
      if(error != null){
          model.addAttribute("error", error);
      }

      return "login";
  }

  @PostMapping("/login")
    public String login(@ModelAttribute("user") UserDTO userDTO, HttpServletRequest request, Model model){

      User user = userService.getUserByEmailAndPassword(userDTO);

      if(user == null){

          return "redirect:/user/login?error=incorrect login details";
      }

      request.getSession().invalidate();;
      HttpSession session = request.getSession(true);
      session.setAttribute("userId", user.getId());
      session.setAttribute("name", user.getName());

      return "redirect:/";
  }

  @GetMapping("/logout")
    public String logout( HttpServletRequest request){
       HttpSession session = request.getSession();
       session.removeAttribute("userId");
       session.invalidate();
       return "redirect:/user/login";
  }


  @GetMapping("/signup")
    public String signupPage(HttpServletRequest request, Model model){


        Long id = Others.checkIfLogin(request);
        if(id != null){
            return "redirect:/";
        }

        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);

        return "register";
    }


    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") UserDTO userDTO,  HttpServletRequest request, Model model){

      Boolean check = userService.findUserByEmail(userDTO);

      if(check){
          model.addAttribute("error", "email already exist, login");
          model.addAttribute("user", userDTO);
          return "register";
      }

      User user = new User(userDTO);
      userService.saveUser(user);
      return "redirect:/user/login";
    }

}
