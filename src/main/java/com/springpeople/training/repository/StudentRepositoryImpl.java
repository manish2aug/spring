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

import com.springpeople.training.domain.Student;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Collection<String> getStudentNames() {
		final Collection<String> studentNames = new ArrayList<>();
		List<Student> query = template.query("SELECT * FROM STUDENT", new HashMap<String, Object>(), new StudentMapper());
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
	
	private static final class StudentMapper implements RowMapper<Student> {

		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student s = new Student();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setEmail(rs.getString("email"));
			return s;
		}

	}

}
