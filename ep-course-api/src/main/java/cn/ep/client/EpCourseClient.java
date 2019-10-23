package cn.ep.client;

import cn.ep.bean.EpCourse;
import cn.ep.utils.ResultVO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "ep-course",
        configuration = FeignConfig.class
)
public interface EpCourseClient {


    @GetMapping(value = "/list/{key}/{page}")
    ResultVO getListByKeyAndPage(@PathVariable String key, @PathVariable int page);

    @GetMapping(value = "top/list/{free}")
    ResultVO getListTopByFree(@PathVariable int free);

    @GetMapping(value = "carouse/list")
    ResultVO getCarouselList();



    @GetMapping(value = "recommend/list")
    ResultVO getRecommendList();


    @GetMapping(value = "/list/{kindId}/{free}/{order}/{page}")
    ResultVO getListByKindIdAndFreeAndOrderAndPage(@PathVariable long kindId, @PathVariable int free, @PathVariable int order,@PathVariable int page);

    @GetMapping(value = "verse/{courseId}")
    ResultVO getVerseInfoByCourseId(@PathVariable long courseId);


    @GetMapping(value = "/{courseId}")
    ResultVO getCourseInfoByCourseId(@PathVariable long courseId);


    @PostMapping("")
    ResultVO insert(@RequestBody EpCourse course);

    @GetMapping("/current/list")
    ResultVO getListByCurrentUserId();

    @PostMapping("/subscription/{courseId}")
    ResultVO subscription(@PathVariable long courseId);

    @GetMapping("/subscription/list")
    ResultVO getSubscriptionList();
}

