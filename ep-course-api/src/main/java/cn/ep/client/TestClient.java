package cn.ep.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ep-course", configuration = FeignConfig.class)
public interface TestClient {

}
