package com.journaldev.spring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.model.Employee;
import com.journaldev.spring.service.EmployeeService;

// 看看这些aspects如何切入bean方法:

public class SpringMainXMLConfigAspect {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		// 参数1：要检索的bean的名字
		EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);
		
		System.out.println("2-----------Dividing line----------");
		
		System.out.println(employeeService.getEmployee().getName());
		
		employeeService.getEmployee().setName("xzj");
		
		// employeeService.getEmployee().throwException();
		
		ctx.close();
		
		// 从console可以看到advices 根据其切入点配置(pointcut configurations)逐个执行。
	}
	
}
