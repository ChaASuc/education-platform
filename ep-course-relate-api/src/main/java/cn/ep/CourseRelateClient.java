package cn.ep;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author hel
 * @Create 2019/10/15
 * @Description
 * @Since 1.0.0
 */
@FeignClient(
        name = "ep-category",
        configuration = FeignConfig.class
)
public interface CourseRelateClient {

}
