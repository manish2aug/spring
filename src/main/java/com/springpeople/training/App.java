package com.springpeople.training;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springpeople.training.configuration.ApplicationConfiguration;
import com.springpeople.training.service.StudentService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        StudentService service = context.getBean(StudentService.class);
        System.out.println(service.getStudentNames());
        System.out.println(service.getStudentEmails());
    }
}
