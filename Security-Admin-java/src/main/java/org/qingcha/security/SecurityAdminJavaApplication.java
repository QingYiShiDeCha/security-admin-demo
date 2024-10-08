package org.qingcha.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.qingcha.security.mapper")
public class SecurityAdminJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAdminJavaApplication.class, args);
    }

}
