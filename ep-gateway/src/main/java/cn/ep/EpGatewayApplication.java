package cn.ep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy  // 开启Zuul的网关功能
@EnableDiscoveryClient
public class EpGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpGatewayApplication.class, args);
    }


}
