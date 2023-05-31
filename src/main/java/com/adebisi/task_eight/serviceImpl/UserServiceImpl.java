package com.adebisi.task_eight.serviceImpl;

import com.adebisi.task_eight.DTO.UserDTO;
import com.adebisi.task_eight.model.User;
import com.adebisi.task_eight.repo.UserRepo;
import com.adebisi.task_eight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepo userRepo;

    @Autowired
    public  UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public User getUserByEmailAndPassword(UserDTO userDTO) {
      Optional<User> optionalUser = userRepo.findUsersByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
      if(optionalUser.isPresent()){
          return optionalUser.get();
      }else{
          return null;
      }

    }

    @Override
    public User findUserById(Long id) {
        User user = userRepo.findById(id).get();
        return user;
    }

    @Override
    public Boolean findUserByEmail(UserDTO userDTO) {
        Optional<User> user = userRepo.findUserByEmail(userDTO.getEmail());
        if(user.isPresent()){
           return true;
        }else {
            return false;
        }
    }

    @Override
    public User saveUser(User user) {
        User user1 = userRepo.save(user);
        return user1;

    }


}
