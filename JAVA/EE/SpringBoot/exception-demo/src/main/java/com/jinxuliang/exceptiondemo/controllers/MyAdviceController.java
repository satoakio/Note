package com.jinxuliang.exceptiondemo.controllers;

import com.jinxuliang.exceptiondemo.domain.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//自定义ControllerAdvice

//限定被标注为@Controller或者@RestController的类才被拦截
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class MyAdviceController {
    public MyAdviceController() {
    }

    // 定义一个ControllerAdvice，在里面针对特定的异常进行处理
    // 异常处理，可以定义异常类型进行拦截处理
    @ExceptionHandler(value = UserNotFoundException.class)
    // 以JSON表达方式响应
    @ResponseBody
    // 定义为服务器错误状态码
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> exception(HttpServletRequest request,
                                         UserNotFoundException ex) {
        Map<String, Object> msgMap = new HashMap<>();
        // 获取异常信息
        msgMap.put("code", ex.getId());
        // 可以在此添加任何需要发送给客户端的信息
        msgMap.put("message", "发生时间："+new Date());
        return msgMap;
    }
}

//access:localhost:8080/user/1(GET method)
//当控制器方法中抛出未捕获的UserNotFoundExceptions时，
//前面自定义的异常处理器被调用，客户端将得到以下信息：