package com.adebisi.task_eight.service;

import com.adebisi.task_eight.DTO.UserDTO;
import com.adebisi.task_eight.model.User;

public interface UserService {

    User getUserByEmailAndPassword(UserDTO userDTO);

    User findUserById(Long id);

    Boolean findUserByEmail(UserDTO userDTO);

    User saveUser(User user);
}
