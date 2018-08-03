package com.springpeople.training.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@Import(InfrastructureConfiguration.class)
@ComponentScan(basePackages = { "com.springpeople.training" })
public class ApplicationConfiguration {

	@Autowired
	private DataSource dataSource;

	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

//	@Bean
//	public DataSource dataSource() {
//		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder()
//				.setType(EmbeddedDatabaseType.HSQL);
//		embeddedDatabaseBuilder.addScript("db/sql/create-db.sql").addScript("db/sql/insert-data.sql");
//		DriverManagerDataSource ds = new DriverManagerDataSource();
//		ds.setDriverClassName("com.mysql.jdbc.Driver");
//		ds.setUrl("jdbc:hsqldb:mem:testdb");
//		ds.setUsername("sa");
//		ds.setPassword("--password");
//		return ds;
//	}
}
