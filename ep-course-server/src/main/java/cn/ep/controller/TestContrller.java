package cn.ep.controller;

import cn.ep.annotation.CanLook;
import cn.ep.annotation.CanModify;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequestMapping("ep/course")
public class TestContrller {

    @ApiOperation(value="测试", notes="未测试")
    @GetMapping(value = "/test")
   // @CanLook
   // @CanModify
    String test(){
        return "epsilon";
    }
}
