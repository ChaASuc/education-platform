package cn.ep.client;

import cn.ep.utils.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(
        name = "ep-course",
        configuration = FeignConfig.class
)
public interface EpCheckClient {

    @PutMapping("")
    ResultVO check(@RequestBody  Map<String,String> params);

    @GetMapping("/list/{page}")
    ResultVO getListByPage(@PathVariable int page);


    @GetMapping("/current/list/{type}/{page}")
    ResultVO getCurrentUserListByType(@PathVariable int type, @PathVariable int page);
}
