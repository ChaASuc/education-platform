package cn.ep.controller;


import cn.ep.annotation.CanLook;
import cn.ep.annotation.IsLogin;
import cn.ep.courseenum.RoleEnum;
import cn.ep.enums.GlobalEnum;
import cn.ep.service.IChapterService;
import cn.ep.service.ICheckService;
import cn.ep.service.ICourseService;
import cn.ep.service.IKindService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import cn.ep.vo.CheckVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "课程模块：审核接口")
@RequestMapping(value = "ep/course/check")
public class EpCheckController {

    @Autowired private RedisUtil redisUtil;
    @Autowired private ICheckService checkService;
    @Autowired private IChapterService chapterService;
    @Autowired private ICourseService courseService;
    @Autowired private IKindService kindService;

    static class CacheNameHelper{
        public static final String EP_COURSE_CHECK_PREFIX_UESR_ID_TYPE_PAGE = "ep_courseCheck_prefix_UserId_type_page_%s_%s_%s";
        public static final String EP_COURSE_CHECK_PREFIX_GET_CURRENT_USER_LIST = "ep_courseCheck_prefix_getCurrentUserList";
        public static final String EP_COURSE_CHECK_PREFIX_GET_LIST_BY_PAGE = "ep_courseCheck_prefix_getList_%s";
    }

    ResultVO check(){
        return ResultVO.success();
    }

    @ApiOperation(value = "获取所有用户的审核列表（只能查看不能审核）：管理员权限，按时间降序", notes = "开发人员已测试")
    @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "path")
    @GetMapping("/list/{page}")
    @IsLogin
    @CanLook(role = RoleEnum.ADMIN)
    ResultVO getListByPage(@PathVariable  int page){
        String redisKey = CacheNameHelper.EP_COURSE_CHECK_PREFIX_GET_LIST_BY_PAGE;
        Object object = redisUtil.get(redisKey);
        if (object != null)
            return ResultVO.success(object);
        PageInfo<CheckVO> checkVOList = checkService.getListByUserIdAndTypeAndPage(null,0,page);
        redisUtil.set(redisKey,checkVOList);
        return ResultVO.success(checkVOList);
    }

    @ApiOperation(value = "获取当前用户的审核列表：管理员权限，按时间降序", notes = "开发人员已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型：0所有1未审核2已审核", dataType = "int", paramType = "path")
            , @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "path")
    })
    @GetMapping("/current/list/{type}/{page}")
    @IsLogin
    @CanLook(role = RoleEnum.ADMIN)
    ResultVO getCurrentUserListByType(@PathVariable int type, @PathVariable int page){
         if (type != 0 && type != 1 && type != 2){
             return ResultVO.failure(GlobalEnum.OPERATION_ERROR,"参数非法");
         }
         //todo 从用户模块获取当前用户id；
         long userId = 1L;
         String redisKey = CacheNameHelper.EP_COURSE_CHECK_PREFIX_GET_CURRENT_USER_LIST;
         String redisItem = String.format(CacheNameHelper.EP_COURSE_CHECK_PREFIX_UESR_ID_TYPE_PAGE,userId,type,page);
         Object object = redisUtil.hget(redisKey,redisItem);
         if (object != null)
             return ResultVO.success(object);

        PageInfo<CheckVO> checkVOList = checkService.getListByUserIdAndTypeAndPage(userId,type,page);
        redisUtil.hset(redisKey,redisItem,checkVOList);
        return ResultVO.success(checkVOList);
    }
}
