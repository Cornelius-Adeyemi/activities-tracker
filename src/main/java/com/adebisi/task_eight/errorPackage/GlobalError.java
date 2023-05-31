package com.adebisi.task_eight.errorPackage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@ResponseStatus
public class GlobalError {


    @ExceptionHandler(value={Exception.class, RuntimeException.class})
    public ModelAndView handleError(Exception e){

        ModelAndView model = new ModelAndView();
        model.setViewName("error");
        model.addObject("status", HttpStatus.NOT_FOUND);
        model.addObject("error", e.getMessage());

        return model;
    }
}
