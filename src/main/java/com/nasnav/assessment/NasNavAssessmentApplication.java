package com.nasnav.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NasNavAssessmentApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/nas-nav");
		SpringApplication.run(NasNavAssessmentApplication.class, args);
	}

}
