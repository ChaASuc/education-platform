package cn.ep.controller;


import cn.ep.annotation.CanLook;
import cn.ep.annotation.CanModify;
import cn.ep.annotation.IsLogin;
import cn.ep.bean.EpCheck;
import cn.ep.courseenum.CheckEnum;
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
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        public static final String EP_COURSE_CHECK_PREFIX_UESR_ID_TYPE_PAGE_CHECKED = "ep_courseCheck_prefix_UserId_type_page_%s_%s_%s_%s";
        public static final String EP_COURSE_CHECK_PREFIX_GET_CURRENT_USER_LIST = "ep_courseCheck_prefix_getCurrentUserList";
        public static final String EP_COURSE_CHECK_PREFIX_GET_LIST_BY_PAGE_CHECKED = "ep_courseCheck_prefix_getList_%s_%s";
    }

    @ApiOperation(value = "审核，管理员权限",notes = "开发人员已测试")
   /* @ApiImplicitParams({
            @ApiImplicitParam(name = "checkId", value = "审核记录id", dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "status", value = "状态，1未通过，2通过", dataType = "int", paramType = "body")
    })*/
  //  @ApiImplicitParam(name = "params" , paramType = "body", )

    @PutMapping("")
    @IsLogin
    @CanModify(role = {RoleEnum.ADMIN})
    ResultVO check(@RequestBody @ApiParam(value = "json格式，checkId:审核记录id，status：状态【1：未通过2：通过】")  Map<String,String> params){
        long checkId = Long.valueOf(params.get("checkId"));
        int status = Integer.valueOf(params.get("status"));
        CheckEnum checkEnum = checkService.checkAndSetStatus(checkId,status);

        if (checkEnum == CheckEnum.CHECK_VIDEO){
            //todo 清除有关章节缓存,可能还有其他
            //只在这里使用到了，还有一处已在别处（updateWatchRecord）清除
            redisUtil.delFuz(EpCourseController.CacheNameHelper.EP_COURSE_PREFIX_GET_COURSEINFO);
        } else if (checkEnum == CheckEnum.CHECK_COURSE){
            //todo 清除有关课程缓存，可能还有其他
            //我只是简单的清除所有缓存
            redisUtil.delFuz(EpCourseController.CacheNameHelper.EP_COURSE_PREFIX);
        } else {
            //todo 清除有关种类缓存,可能还有其他
            //我只是简单的清除所有缓存

            redisUtil.delFuz(EpCourseKindController.CacheNameHelper.EP_COURSE_KIND_PREFIX);
        }
        return ResultVO.success();
    }

    @ApiOperation(value = "获取所有管理员的审核列表（只能查看不能审核）：管理员权限，按时间降序", notes = "开发人员已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "path")
            ,@ApiImplicitParam(name = "checked", value = "获取审核类型：0种类，1视频，2课程", dataType = "int", paramType = "path")
    })@GetMapping("/list/{page}/{checked}")
    @IsLogin
    @CanLook(role = RoleEnum.ADMIN)
    ResultVO getListByPage(@PathVariable  int page, @PathVariable int checked){
        if (checked != 0 && checked != 1 && checked != 2){
            return ResultVO.failure(GlobalEnum.OPERATION_ERROR,"参数非法");
        }
        String redisKey = String.format(CacheNameHelper.EP_COURSE_CHECK_PREFIX_GET_LIST_BY_PAGE_CHECKED,page,checked);
        Object object = redisUtil.get(redisKey);
        if (object != null)
            return ResultVO.success(object);
        PageInfo<CheckVO> checkVOList = checkService.getAllCheckListByPageAndChecked(page,checked);
        redisUtil.set(redisKey,checkVOList);
        return ResultVO.success(checkVOList);
    }

    @ApiOperation(value = "获取当前管理员的审核列表：管理员权限，按时间降序", notes = "开发人员已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "状态呢：0所有1未审核2已审核", dataType = "int", paramType = "path")
            , @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "path")
            ,@ApiImplicitParam(name = "checked", value = "获取审核类型：0种类，1视频，2课程", dataType = "int", paramType = "path")
    })
    @GetMapping("/current/list/{type}/{page}/{checked}")
    @IsLogin
    @CanLook(role = RoleEnum.ADMIN)
    ResultVO getCurrentUserListByType(@PathVariable int type, @PathVariable int page, @PathVariable int checked){
         if (type != 0 && type != 1 && type != 2){
             return ResultVO.failure(GlobalEnum.OPERATION_ERROR,"参数非法");
         }
        if (checked != 0 && checked != 1 && checked != 2){
            return ResultVO.failure(GlobalEnum.OPERATION_ERROR,"参数非法");
        }
         //todo 从用户模块获取当前用户id；
         long userId = 1L;
        String redisKey = CacheNameHelper.EP_COURSE_CHECK_PREFIX_GET_CURRENT_USER_LIST;
        String redisItem = String.format(CacheNameHelper.EP_COURSE_CHECK_PREFIX_UESR_ID_TYPE_PAGE_CHECKED,userId,type,page,checked);
        Object object = redisUtil.hget(redisKey,redisItem);
         if (object != null)
             return ResultVO.success(object);

        PageInfo<CheckVO> checkVOList = checkService.getListByUserIdAndTypeAndPageAndChecked(userId,type,page,checked);
        redisUtil.hset(redisKey,redisItem,checkVOList);
        return ResultVO.success(checkVOList);
    }
}
