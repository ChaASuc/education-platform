package cn.ep.controller;

import cn.ep.exception.GlobalException;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.converters.Auto;
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
@RequestMapping("/product")
public class ProductController {

    @RequestMapping("")
    public String getMessage() {
        return "hello";
    }

    @RequestMapping("/{id}")
    public String getMessageById(@PathVariable Long id) throws InterruptedException {
        throw new GlobalException(1, "自定义");
    }

}
