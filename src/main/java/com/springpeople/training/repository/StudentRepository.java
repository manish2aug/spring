package com.springpeople.training.repository;

import java.util.Collection;

import com.springpeople.training.CustomException;
import com.springpeople.training.domain.Student;

public interface StudentRepository {
	Collection<String> getStudentNames();
	Collection<String> getStudentEmails();
	void createStudent(Student student);
	void updateStudent(String name);
	void performTransaction1() throws CustomException;
}
