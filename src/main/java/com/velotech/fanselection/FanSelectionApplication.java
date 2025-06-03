package com.velotech.fanselection;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@Profile("client")
@ServletComponentScan
@ComponentScan("com.velotech.fanselection")
@EntityScan(basePackages = { "com.velotech.fanselection.models" })
public class FanSelectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FanSelectionApplication.class, args);
	}

	 

}
