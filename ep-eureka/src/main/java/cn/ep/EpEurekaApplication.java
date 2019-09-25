package cn.ep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author deschen
 * @Create 2019/9/12
 * @Description
 * @Since 1.0.0
 */
@SpringBootApplication
@EnableEurekaServer   // 声明这个应用是一个Eureka注册中心
public class EpEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpEurekaApplication.class, args);
    }
}
