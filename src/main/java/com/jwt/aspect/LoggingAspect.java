package com.jwt.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.jwt.common.MailAPI;
import com.jwt.model.Employee;

@Aspect
public class LoggingAspect {

	@AfterReturning("execution(* com.jwt.controller.EmployeeController.saveEmployee(..))")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println("logBefore() is running!");
		System.out.println("hijacked : " + joinPoint.getArgs());
		Employee emp = (Employee)joinPoint.getArgs()[0];
		System.out.println(emp.getEmail()); 
		  //from,password,to,subject,message  
		MailAPI.send("er.shubjain@gmail.com","15july1993",emp.getEmail(),"Registration","You have been registered successfully...", emp);  
	     //change from, password and to  
		System.out.println("******");
	}

}