package com.moneybug.bug;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.moneybug.bug.**.mapper")
public class BugApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugApplication.class, args);
	}

}
