package com.jinxuliang.spring_advanced_demo.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
// 有的时候，我们需要设置一些“影响全局”的配置，这时，可以
//使用@ControllerAdivice完成这个工作

//测试：http://localhost:8080/mvc/advice
@ControllerAdvice
public class MyControllerAdvice {
    // 在上面的例子中，我们定义了一个ControllerAdvice类，
    // 并使用@ExceptionHandler将一个方法标识为“全局的出错处理程序”。

    //配置一个全局的出错处理程序，应用于所有控制器中的所有方法
    @ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception exception, WebRequest request){
        //需要创建error.html
        ModelAndView modelAndView=new ModelAndView("error");//注意这里指定了一个视图模板——error
        modelAndView.addObject("errorMessage",exception.getMessage());
        modelAndView.addObject("now",new Date().toString());
        return modelAndView;
    }
}

//视图模板文件error.html定义了出错信息显示页面的内容。
// 其中“${…}”中的信息从ControllerAdvice对象的exception()方法中传入。这 是Thymeleaf模板语法。