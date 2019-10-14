package cn.ep.controller;

import cn.ep.bean.EpCourse;
import cn.ep.service.ICourseService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Api(description = "课程模块：课程接口")
@RequestMapping("ep/course")
public class EpCourseController {

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{

        // ep_category_prefix_getByUserNicknameAnduserPwd_{用户名}_{密码}
        public static final String EP_COURSE_PREFIX_GET_LIST_BY_KEY_AND_PAGE =
                "ep_user_prefix_getListByKeyAndPage_%s_%s";

        public static final String EP_COURSE_PREFIX_GET_LIST_TOP_BY_FREE =
                "ep_user_prefix_getListTopByFree_%s";
        //ep_user_prefix_* 用于全部删除，避免缓存
        public static final String EP_COURSE_PREFIX = "ep_course_prefix_*";
    }


    @Autowired
    private ICourseService courseService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value="测试", notes="未测试")
    @GetMapping(value = "/test")
   // @CanLook
   // @CanModify
    String test(){
        return "epsilon";
    }



    @ApiOperation(value = "网站搜索栏接口,只支持对课程名称、课程目标、课程介绍全文搜索", notes = "未测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "搜索关键字", dataType = "String", paramType = "path")
            , @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "path")
    })
    @GetMapping(value = "/list/{key}/{page}")
    ResultVO getListByKeyAndPage(@PathVariable String key, @PathVariable int page){
        String redisKey = String.format(CacheNameHelper.EP_COURSE_PREFIX_GET_LIST_BY_KEY_AND_PAGE, key, page);
        Object obj = redisUtil.get(redisKey);
        if (obj != null){
            return ResultVO.success(obj);
        }
        PageHelper.startPage(page,1);
        List<EpCourse> courses = courseService.getListByKey(key,1);
        PageInfo<EpCourse> info = new PageInfo<>(courses);
        redisUtil.set(key,info,1800, TimeUnit.SECONDS);
        return ResultVO.success(info);
    }

    @ApiOperation(value = "获取课程排行榜，以时间+订阅为排行依据，非实时，次日更新", notes = "未测试")
    @ApiParam(name = "free", value = "查询是否免费:0为免费，1为收费")
    @GetMapping(value = "list/top/{free}")
    ResultVO getListTopByFree(@PathVariable int free){
        String redisKey = String.format(CacheNameHelper.EP_COURSE_PREFIX_GET_LIST_TOP_BY_FREE,free);
        Object object = redisUtil.get(redisKey);
        if (object != null)
            return ResultVO.success(object);
        List<EpCourse> courses = courseService.getListByTop(20,free,1);
        redisUtil.set(redisKey,courses);
        return ResultVO.success(courses);
    }

}

