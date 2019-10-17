package cn.ep.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ep-course", configuration = FeignConfig.class)
public interface TestClient {

    @GetMapping(value = "/test")
    String test();
}
