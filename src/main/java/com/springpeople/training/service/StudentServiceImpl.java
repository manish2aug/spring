package com.springpeople.training.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springpeople.training.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository repository;
	
	public Collection<String> getStudentNames() {
		return repository.getStudentNames();
	}

	@Override
	public Collection<String> getStudentEmails() {
		return repository.getStudentEmails();
	}

}
