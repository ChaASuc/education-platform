package cn.ep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.sleuth.sampler.SamplerProperties;
import org.springframework.cloud.sleuth.zipkin2.ZipkinProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
@EnableFeignClients // 启动Feign组件
@EnableHystrix
@EnableSwagger2
@MapperScan({"cn.ep.mapper"})
public class EpProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpProductApplication.class, args);
    }

    @ConfigurationProperties("spring.sleuth.sampler")
    @RefreshScope
    public SamplerProperties samplerProperties() {
        return new SamplerProperties();
    }

    @ConfigurationProperties("spring.zipkin")
    @RefreshScope
    public ZipkinProperties zipkinProperties() {
        return new ZipkinProperties();
    }
}
