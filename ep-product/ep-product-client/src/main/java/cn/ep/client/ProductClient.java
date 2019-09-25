package cn.ep.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author deschen
 * @Create 2019/9/15
 * @Description
 * @Since 1.0.0
 */
@FeignClient(
        name = "ep-product",
        configuration = FeignConfig.class
)
public interface ProductClient {

    @RequestMapping("/product")
    String getMessage();

    @RequestMapping("/product/{id}")
    String getMessageById(@PathVariable Long id);

//    @Component
//    class ProductClientFallback implements ProductClient {
//
//        @Override
//        public String getMessage() {
//            return "feign熔断";
//        }
//
//        @Override
//        public String getMessageById(Long id) {
//            return "feign熔断";
//        }
//    }
}
