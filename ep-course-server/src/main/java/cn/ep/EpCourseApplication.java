package cn.ep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("cn.ep.mapper")
@EnableSwagger2
public class EpCourseApplication {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SpringApplication.run(EpCourseApplication.class, args);
    }

}
