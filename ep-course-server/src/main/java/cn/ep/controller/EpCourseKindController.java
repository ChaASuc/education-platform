package cn.ep.controller;

import cn.ep.annotation.CanAdd;
import cn.ep.annotation.IsLogin;
import cn.ep.bean.EpCourse;
import cn.ep.bean.EpCourseKind;
import cn.ep.courseenum.CourseEnum;
import cn.ep.courseenum.CourseKindEnum;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.service.ICourseService;
import cn.ep.service.IKindService;
import cn.ep.utils.IdWorker;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@Api(description = "课程模块：课程种类接口：完成")
@RequestMapping("api/ep/course")
public class EpCourseKindController {


    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{

        // ep_category_prefix_getByUserNicknameAnduserPwd_{用户名}_{密码}
        public static final String EP_COURSE_KIND_PREFIX_GET_LIST_ALL =
                "ep_courseKind_prefix_getListAll";
        public static final String EP_COURSE_KIND_PREFIX_GET_LIST_TOP =
                "ep_courseKind_prefix_getListTop";
        // 以每个种类的Id作为键值
        public static final String EP_COURSE_KIND_PREFIX_KIND_ID =
                "ep_courseKind_prefix_kind_%s";
        //ep_user_prefix_* 用于全部删除，避免缓存
        public static final String EP_COURSE_KIND_PREFIX = "ep_courseKind_prefix_*";
        public static final String EP_COURSE_KIND_PREFIX_GET_LIST_BY_ROOT = "ep_courseKind_prefix_getListByRoot";
    }


    @Autowired
    private IKindService kindService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "获得所有课程种类", notes = "开发人员已测试")
    @GetMapping("/listAll")
    ResultVO getListAll(){
        Object obj = redisUtil.get(CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_ALL);
        if (obj != null)
            return ResultVO.success(obj);
        Map<EpCourseKind,List<EpCourseKind>> kindListMap = kindService.getListByStatus(CourseKindEnum.VALID_STATUS.getValue());
        redisUtil.set(CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_ALL,kindListMap);
        return ResultVO.success(kindListMap);
    }

    @ApiOperation(value = "获得热门种类：8条记录，以点击量为依据，实时更新", notes = "测试人员已测试")
    @GetMapping("/list/kindtop")
    ResultVO getListTop(){
        String redisKey = CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_TOP;
        Map<Object, Object> map = redisUtil.hmget(redisKey);
        if (map != null){
            //System.out.println(redisKey);
            List<EpCourseKind> kindList = new LinkedList<>();
            for (Map.Entry<Object,Object> entry:
                 map.entrySet()) {
                kindList.add((EpCourseKind) entry.getValue());
            }
            kindList.sort(Comparator.comparing(EpCourseKind::getSearchCount).reversed());

            return ResultVO.success(kindList.subList(0,kindList.size()>=8?8:kindList.size()));
        }
        List<EpCourseKind> kindList = kindService.getSubKindList();
        //System.out.println(kindList);
        for (EpCourseKind kind :
                kindList) {
            redisUtil.hset(CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_TOP
                    ,String.format(CacheNameHelper.EP_COURSE_KIND_PREFIX_KIND_ID,kind.getId())
                    ,kind);
        }
        //System.out.println(CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_TOP);
        return  ResultVO.success(kindList.subList(0,kindList.size()>=8?8:kindList.size()));
    }

    @ApiOperation(value = "获取[一级|二级]种类，通过root区分", notes = "未测试")
    @ApiImplicitParam(name="root",value = "root: 0值表示获取所有一级种类，其他值为一级种类的id，表示此一级种类的所有二级种类", dataType = "long", paramType = "path")
    @GetMapping("list/list/{root}")
    ResultVO getListByRoot(@PathVariable long root){
        String redisKey = CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_BY_ROOT;
        Object obj = redisUtil.get(redisKey);
        if (obj != null)
            return ResultVO.success(obj);
        List<EpCourseKind> kindList = kindService.getListByRootAndStatus(CourseKindEnum.VALID_STATUS.getValue(),root);
        redisUtil.set(redisKey,kindList);
        return ResultVO.success(kindList);
    }


    @ApiOperation(value = "增加一个种类", notes = "未测试")
    @ApiImplicitParam(name="courseKind",value = "种类实体类:其中kindName必传，root必传：0为一级种类，其他值为一级种类的id", dataType = "EpCourseKind")
    @PostMapping("")
    @IsLogin
    @CanAdd
    ResultVO insert(@RequestBody EpCourseKind courseKind){
        System.out.println(courseKind);
        //将排行榜数据更新进数据库
        String redisKey = CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_TOP;
        Map<Object, Object> map = redisUtil.hmget(redisKey);
        if (map != null) {
            //System.out.println(redisKey);
            List<EpCourseKind> kindList = new LinkedList<>();
            for (Map.Entry<Object, Object> entry :
                    map.entrySet()) {
                kindList.add((EpCourseKind) entry.getValue());
            }
            for (int i = 0; i < kindList.size(); i++) {
                EpCourseKind kind = new EpCourseKind();
                kind.setId(kindList.get(i).getId());
                kind.setSearchCount(kindList.get(i).getSearchCount());
                if (!kindService.updateById(kind))
                    throw new GlobalException(GlobalEnum.SERVICE_ERROR);
            }
        }
        if (!kindService.insertAndSendCheck(courseKind))
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"插入失败");
        return  ResultVO.success();
    }

    @ApiOperation(value = "清除缓存")
    @GetMapping("coursekind/clear")
    ResultVO clear(){
        redisUtil.delFuz(CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_TOP);
        return ResultVO.success();
    }

}

