package cn.ep.config;

import cn.ep.utils.IdWorker;
import org.junit.Before;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author deschen
 * @Create 2019/10/2
 * @Description
 * @Since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(IdWorkerProperties.class)
public class IdWorkerConfig {

    /**
     * 注册idWorker
     * @param idWorkerProperties
     * @return
     */
    @Bean
    public IdWorker idWorker(IdWorkerProperties idWorkerProperties) {
        return new IdWorker(idWorkerProperties.getWorkerId(), idWorkerProperties.getDatacenterId());
    }
}
