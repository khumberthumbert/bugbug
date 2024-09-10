package com.moneybug.bug;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.moneybug.bug.**.mapper")
//@EnableCaching
public class BugApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugApplication.class, args);
	}
}
