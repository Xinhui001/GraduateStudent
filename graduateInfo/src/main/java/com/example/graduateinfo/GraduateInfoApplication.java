package com.example.graduateinfo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 20891
 */
@SpringBootApplication
@MapperScan("com.example.graduateinfo.mapper")
public class GraduateInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduateInfoApplication.class, args);
    }

}
