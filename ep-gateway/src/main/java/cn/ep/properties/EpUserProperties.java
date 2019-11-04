package cn.ep.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ep")
public class EpUserProperties {

    private RoleProperties role = new RoleProperties();

    private DeptProperties dept = new DeptProperties();
}
