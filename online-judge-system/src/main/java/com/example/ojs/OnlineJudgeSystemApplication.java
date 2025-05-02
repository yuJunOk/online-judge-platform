package com.example.ojs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pengYuJun
 */
@MapperScan("com.example.ojs.mapper")
@SpringBootApplication
public class OnlineJudgeSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineJudgeSystemApplication.class, args);
    }

}
