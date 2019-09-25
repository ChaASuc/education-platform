package cn.ep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.sampler.SamplerProperties;
import org.springframework.cloud.sleuth.zipkin2.ZipkinProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author deschen
 * @Create 2019/9/12
 * @Description
 * @Since 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient  // 开启EurekaClient功能,兼容不同技术 zookeeper等
@EnableSwagger2
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
