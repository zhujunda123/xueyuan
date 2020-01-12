package com.online.edu.eduservice.handler;

import com.online.edu.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobaExceptionHandler {

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e){
        e.printStackTrace();
        return  R.error().message(e.getMessage()).code(e.getCode());
    }
}



