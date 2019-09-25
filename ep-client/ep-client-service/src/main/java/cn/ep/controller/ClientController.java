package cn.ep.controller;

//import com.ep.Client;
import cn.ep.client.ProductClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author deschen
 * @Create 2019/9/12
 * @Description
 * @Since 1.0.0
 */
@RestController
@RequestMapping("/client")
@Slf4j
@RefreshScope
@Api(description = "测试客户端API接口")
public class ClientController {

    @Value("${env}")
    private String env;
//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private ProductClient client;


    @ApiOperation(value = "获取env")
    @RequestMapping("/env")
    public String getEnv() {
        return env;
    }

    @RequestMapping("")
    public String getMessage() {
//        List<ServiceInstance> instances =
//                discoveryClient.getInstances("EP-SERVER");
//        log.info("【消费端】url = http://{}:{}/server",
//                instances.get(0).getHost(), instances.get(0).getPort());
//
//        String url =
//                String.format("http://%s:%s/server", instances.get(0).getHost(), instances.get(0).getPort());
//        return restTemplate.getForObject(url, String.class);
        String message = null;
        try {
            message = client.getMessage();
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    @RequestMapping("/feign/{id}")
    public String getFeignMessage(@PathVariable Long id) throws InterruptedException {
        return client.getMessageById(id);
    }
////
//////    @RequestMapping("/ribbon")
//////    public String getRibbonMessage() {
//////        try {
//////            int i = 1 / 0;
//////        } catch (Exception e) {
//////            throw new RuntimeException("异常错误");
//////        }
//////        return restTemplate.getForObject("http://EP-SERVER/server", String.class);
////////        return client.getMessage();
//////    }
//////
//////    /**
//////     * 超过设置熔断时间或者报异常，就会调用fallbackMethod方法，异常处理器未接受
//////     *
//////     * @return
//////     */
//////     @RequestMapping("/hystrix/{id}")
//////////    @HystrixCommand(fallbackMethod = "getHystrixMessageFallBack")
////////    @HystrixCommand(
////////            fallbackMethod = "getHystrixMessageFallBack",   // 设置默认熔断方法
////////            commandProperties = {
////////                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"), // 默认1000ms
////////                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),  // 出发熔断最少请求次数，默认20
////////                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),  // 休眠时长，从开启到半关闭，默认5000ms
////////                    @HystrixProperty(name= "circuitBreaker.errorThresholdPercentage", value = "50")  // 触发熔断请求最小百分比，默认50
//////            })
//////    public String getHystrixMessage(@PathVariable Integer id) {
//////        try {
//////            int i = 1 / 0;
//////        } catch (Exception e) {
//////            throw new RuntimeException("异常错误");
//////        }
//////        try {
//////            Thread.sleep(3000);
//////        } catch (InterruptedException e) {
//////            e.printStackTrace();
//////        }
//////        if (id % 2 == 0) {
//////            throw new RuntimeException("异常错误");
//////        }
//////        return restTemplate.getForObject("http://EP-SERVER/server", String.class);
////////         return client.getMessage();
//////    }
////
////    /**
////      * 超过设置熔断时间，就会调用Feign的熔错方法，而报异常不处理
////      *
////      * @return
////      */
////    @RequestMapping("/feign/{id}")
////    public String getFeignMessage(@PathVariable Long id) throws InterruptedException {
//////        if (id % 2 == 0) {
//////            Thread.sleep(2);
//////        }
////        return client.getMessageById(id);
////    }
//////
//////    // 全局熔断不能有参数
//////    public String hystrixFallBack() {
//////        return "全局熔断开始";
//////    }
//////
//////    // getHystrixMessage带其参数
//////    public String getHystrixMessageFallBack(Integer id) {
//////        return "getHystrixMessage方法熔断 开始";
//////    }
}
