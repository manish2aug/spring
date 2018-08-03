package com.springpeople.training.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springpeople.training.CustomException;
import com.springpeople.training.domain.Student;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public Collection<String> getStudentNames() {
		final Collection<String> studentNames = new ArrayList<>();
		List<Student> query = template.query("SELECT * FROM STUDENT", new HashMap<String, Object>(),
				new StudentMapper());
		query.forEach(a -> studentNames.add(a.getName()));
		return studentNames;
	}

	@Override
	public Collection<String> getStudentEmails() {
		final Collection<String> studentEmails = new ArrayList<>();
		Collection<Student> query = jdbcTemplate.query("SELECT * FROM STUDENT", new StudentMapper());
		query.forEach(a -> studentEmails.add(a.getEmail()));
		return studentEmails;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = CustomException.class)
	public void createStudent(Student student) {
		jdbcTemplate.update("INSERT INTO STUDENT VALUES(?, ?, ?)", student.getId(), student.getName(),
				student.getEmail());
		getStudentNames();
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
	public void updateStudent(String name) {
		jdbcTemplate.update("UPDATE STUDENT SET NAME=? where ID=?)", name, 4);
	}

	private static final class StudentMapper implements RowMapper<Student> {

		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student s = new Student();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setEmail(rs.getString("email"));
			return s;
		}
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = CustomException.class)
	public void performTransaction1() throws CustomException {
		// Step 1: perform a successful operation
		jdbcTemplate.update("INSERT INTO STUDENT VALUES(?, ?, ?)", 4, "step1Name", "d@d.com");
		// Step 2: perform a read operation to check isolation level
		Collection<String> studentNames = getTotalStudentNames();
		System.out.println("In performTransaction1() total students are: " + studentNames);
		// Step 3: call a method
		performTransaction2();
	}

	private Collection<String> getTotalStudentNames() {
		List<Student> query = template.query("SELECT * FROM STUDENT", new HashMap<String, Object>(),
				new StudentMapper());
		final Collection<String> studentNames = new ArrayList<>();
		query.forEach(a -> studentNames.add(a.getName()));
		return studentNames;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = CustomException.class)
	private void performTransaction2() throws CustomException {
		jdbcTemplate.update("INSERT INTO STUDENT VALUES(?, ?, ?)", 5, "step2Name", "e@e.com");
		Collection<String> studentNames = getTotalStudentNames();
		System.out.println("In performTransaction2(), before exception total students are: " + studentNames);
		throw new CustomException();
	}
}
