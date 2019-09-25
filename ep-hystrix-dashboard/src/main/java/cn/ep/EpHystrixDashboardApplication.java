package cn.ep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
@RefreshScope
public class EpHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpHystrixDashboardApplication.class, args);
    }

}
