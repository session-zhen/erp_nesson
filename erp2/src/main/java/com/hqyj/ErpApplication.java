package com.hqyj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan(basePackages="com.hqyj.mapper")
/*@ImportResource("classpath:dubbo-consumer.xml")*/
public class ErpApplication {
	public static void main(String[] args) {
		SpringApplication.run(ErpApplication.class, args);
	}
}
