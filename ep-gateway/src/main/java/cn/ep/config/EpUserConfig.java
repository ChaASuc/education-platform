package cn.ep.config;

import cn.ep.properties.EpUserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(EpUserProperties.class)
@Configuration
public class EpUserConfig {
}
