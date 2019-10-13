package cn.ep.controller;

import cn.ep.service.ICourseService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "课程模块：对课程操作提供服务")
@RequestMapping("ep/course")
public class EpCourseContrller {

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{

        // ep_category_prefix_getByUserNicknameAnduserPwd_{用户名}_{密码}
        public static final String EP_COURSE_PREFIX_GETBYUSERNICKNAMEANDUSERPWD =
                "ep_user_prefix_getByUserNicknameAnduserPwd_%s_%s";

        //ep_user_prefix_* 用于全部删除，避免缓存
        public static final String EP_COURSE_PREFIX = "ep_user_prefix_*";
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

    @ApiOperation(value = "网站搜索栏接口,只支持对课程名称、课程目标、课程介绍全文搜索", notes = "为测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "搜索关键字", paramType = "String")
            , @ApiImplicitParam(name = "page", value = "页码", paramType = "int")
    })
    @GetMapping(value = "/list/{key}/{page}")
    ResultVO search(String key, int page){
        String redisKey = String.format(CacheNameHelper.EP_COURSE_PREFIX_GETBYUSERNICKNAMEANDUSERPWD, key, page);
        Object obj = redisUtil.get(redisKey);
        if (obj != null){
            return ResultVO.success(obj);
        }
        return ResultVO.success(redisKey);
    }
}

