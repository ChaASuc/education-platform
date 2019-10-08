package cn.ep.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author deschen
 * @Create 2019/10/2
 * @Description
 * @Since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "ep.work")
public class IdWorkerProperties {

    private long workerId = 1;
    // 数据标识id部分
    private long datacenterId = 1;
}
