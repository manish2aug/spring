package com.springpeople.training;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springpeople.training.configuration.ApplicationConfiguration;
import com.springpeople.training.domain.Student;
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
        Collection<String> studentNames = service.getStudentNames();
        System.out.println("Initial data: " + studentNames); 
        try {
			service.performTransaction1();
		} catch (CustomException e) {
			service.getStudentNames();
			e.printStackTrace();
		}
    }
}
