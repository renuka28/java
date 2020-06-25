package org.renuka.learn.java.security.dbjpamysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class DbJpaMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbJpaMysqlApplication.class, args);
	}

}
