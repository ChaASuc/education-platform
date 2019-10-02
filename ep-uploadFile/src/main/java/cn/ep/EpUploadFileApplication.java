package cn.ep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
@MapperScan({"cn.ep.mapper"})
@EnableSwagger2
public class EpUploadFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpUploadFileApplication.class);
    }
}
