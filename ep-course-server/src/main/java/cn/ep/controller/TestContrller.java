package cn.ep.controller;

import cn.ep.annotation.CanLook;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
public class TestContrller {

    @ApiOperation(value="测试", notes="未测试")
    @GetMapping(value = "/test")
    @CanLook
    String test(){
        return "epsilon";
    }
}
