package com.libai.mavendeploy;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 此处的mapperscan必须是tk的
 */

@EnableDubbo
@MapperScan("com.example.test.mavendeploy.mapper")
@SpringBootApplication
public class MavendeployApplication {

    public static void main(String[] args) {
        SpringApplication.run(MavendeployApplication.class, args);
    }

}
