package com.springpeople.training.repository;

import java.util.Collection;

public interface StudentRepository {
	Collection<String> getStudentNames();
	Collection<String> getStudentEmails();
}
