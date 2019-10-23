package cn.ep.client;

import cn.ep.bean.EpCourseKind;
import cn.ep.utils.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@FeignClient(
        name = "ep-course",
        configuration = FeignConfig.class
)
public interface EpCourseKindClient {


    @GetMapping("/list")
    ResultVO getListAll();

    @GetMapping("/list/kindtop")
    ResultVO getListTop();

    @GetMapping("list/{root}")
    ResultVO getListByRoot(@PathVariable long root);


    @PostMapping("")
    ResultVO insert(@RequestBody EpCourseKind courseKind);

/*    @ApiOperation(value = "清除缓存")
    @GetMapping("coursekind/clear")
    ResultVO clear(){
        redisUtil.delFuz(CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_TOP);
        return ResultVO.success();
    }*/

}

