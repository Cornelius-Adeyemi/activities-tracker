package com.adebisi.task_eight.others;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Others {


    public static Long checkIfLogin(HttpServletRequest request){
        HttpSession session = request.getSession();
        Long id = (Long)session.getAttribute("userId");

        return id;

    }
}
