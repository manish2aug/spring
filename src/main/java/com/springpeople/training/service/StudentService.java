package com.springpeople.training.service;

import java.util.Collection;

import com.springpeople.training.CustomException;
import com.springpeople.training.domain.Student;

public interface StudentService {
	Collection<String> getStudentNames();
	Collection<String> getStudentEmails();
	void createStudent(Student student);
	void updateStudent(String name);
	void performTransaction1() throws CustomException;
}
