package cn.ep.controller;

import cn.ep.bean.EpCourse;
import cn.ep.bean.EpCourseKind;
import cn.ep.service.ICourseService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import cn.ep.vo.CourseInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.REException;

import java.util.ArrayList;
import java.util.Comparator;
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
                "ep_course_prefix_getListByKeyAndPage_%s_%s";

        public static final String EP_COURSE_PREFIX_GET_LIST_TOP_BY_FREE =
                "ep_course_prefix_getListTopByFree_%s";
        //ep_user_prefix_* 用于全部删除，避免缓存
        public static final String EP_COURSE_PREFIX = "ep_course_prefix_*";
        public static final String EP_COURSE_PREFIX_GET_RECOMMEND_LIST = "ep_course_prefix_get_recommend_list";
        public static final String EP_COURSE_PREFIX_GET_LIST_BY_KINDID_AND_FREE_ORDER_PAGE = "ep_course_prefix_get_list_by_kindid_and_free_order_page_%s_%s_%s_%s";
        //这是获取redis热点种类的key值
        public static final String EP_COURSE_KIND_PREFIX_KIND_ID =
                "ep_courseKind_prefix_kind_%s";
        public static final String EP_COURSE_KIND_PREFIX_GET_LIST_TOP =
                "ep_courseKind_prefix_getListTop";
        public static final String EP_COURSE_PREFIX_GET_COURSEINFO_LIST = "ep_courseKind_prefix_getCourseInfoList";
        public static final String EP_COURSE_PREFIX_COURSE_ID = "ep_courseKind_prefix_courseId_%s";
        public static final String EP_COURSE_PREFIX_COURSE_ID_AND_USER_ID = "ep_courseKind_prefix_courseId_and_userId_%s_%s";
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



    @ApiOperation(value = "网站搜索栏接口,只支持对课程名称、课程目标、课程介绍全文搜索", notes = "开发人员已测试")
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

    @ApiOperation(value = "获取课程排行榜，20条记录，以时间+订阅为排行依据，非实时，次日更新", notes = "开发人员已测试")
    @ApiImplicitParam(name = "free", value = "查询是否免费:0为免费，1为收费", dataType = "String", paramType = "path")
    @GetMapping(value = "list/top/{free}")
    ResultVO getListTopByFree(@PathVariable int free){
        String redisKey = String.format(CacheNameHelper.EP_COURSE_PREFIX_GET_LIST_TOP_BY_FREE,free);
        Object object = redisUtil.get(redisKey);
        if (object != null)
            return ResultVO.success(object);
        System.out.println(free);
        List<EpCourse> courses = courseService.getListByTop(1,free,20);
        List<CourseInfoVO> courseInfoVOS = new ArrayList<>(21);
        for (EpCourse c :
                courses) {
            CourseInfoVO courseInfoVO = new CourseInfoVO();
            courseInfoVO.setCourse(c);
            courseInfoVO.setAuthor(null);
            courseInfoVO.setChapters(null);
            courseInfoVO.setScope(0);
            courseInfoVOS.add(courseInfoVO);
        }
        redisUtil.set(redisKey,courseInfoVOS,1,TimeUnit.DAYS);
        return ResultVO.success(courseInfoVOS);
    }

    @ApiOperation(value = "获取课程推荐榜，10条记录，以时间+订阅+评分为排行依据，非实时，次日更新", notes = "未测试")
    @GetMapping(value = "list/recommend/")
    ResultVO getRecommendList(){
        String redisKey = CacheNameHelper.EP_COURSE_PREFIX_GET_RECOMMEND_LIST;
        Object object = redisUtil.get(redisKey);
        /*if (object != null)
            return ResultVO.success(object);*/
        List<EpCourse> courses = courseService.getListByTop(1,1,50);
        List<CourseInfoVO> courseInfoVOS = new ArrayList<>(21);
        for (EpCourse c :
                courses) {
            CourseInfoVO courseInfoVO = new CourseInfoVO();
            courseInfoVO.setCourse(c);
            courseInfoVO.setAuthor(null);
            courseInfoVO.setChapters(null);
            courseInfoVO.setScope(0);
            courseInfoVOS.add(courseInfoVO);
        }
        System.out.println(courses);
        courseInfoVOS.sort(Comparator.comparing(CourseInfoVO::getScope).reversed());
        redisUtil.set(redisKey,courseInfoVOS.subList(0,courseInfoVOS.size()<10?courseInfoVOS.size():10),1,TimeUnit.DAYS);
        return ResultVO.success(courseInfoVOS);
    }

    @ApiOperation(value = "获取课程信息，一页40条", notes = "测试人员已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kindId", value = "种类id", dataType = "long", paramType = "path")
            , @ApiImplicitParam(name = "free", value = "是否免费：0免费，1收费", dataType = "int", paramType = "path")
            , @ApiImplicitParam(name = "order", value = "按哪种方式排序，取值为1、2 或其他值：最新（1）最热（2）最新最热（其他任意整数值，即非1、2）", dataType = "int", paramType = "path")
            , @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "path")
    })
    @GetMapping(value = "/list/{kindId}/{free}/{order}/{page}")
    ResultVO getListByKindIdAndFreeAndOrderAndPage(@PathVariable long kindId, @PathVariable int free, @PathVariable int order,@PathVariable int page){

        //搜索特定种类，热度加一
        Object obj = redisUtil.hget(CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_TOP
                ,String.format(CacheNameHelper.EP_COURSE_KIND_PREFIX_KIND_ID,kindId));
        if (obj != null){
            EpCourseKind kind = (EpCourseKind) obj;
            kind.setSearchCount(kind.getSearchCount()+1);
            redisUtil.hset(CacheNameHelper.EP_COURSE_KIND_PREFIX_GET_LIST_TOP
                    ,String.format(CacheNameHelper.EP_COURSE_KIND_PREFIX_KIND_ID,kindId)
                    ,kind);
        }
        String redisKey = String.format(CacheNameHelper.EP_COURSE_PREFIX_GET_LIST_BY_KINDID_AND_FREE_ORDER_PAGE
                ,order,kindId,free,page);
        Object object = redisUtil.get(redisKey);
        if (object != null)
            return ResultVO.success(object);
        PageHelper.startPage(page,40);
        List<EpCourse> courseList = courseService.getListByKindIdAndFreeAndOrder(kindId,free,order);
        redisUtil.set(redisKey,courseList,3,TimeUnit.DAYS);
        return ResultVO.success(courseList);
    }

    ResultVO getCourseInfoListByCourseId(long courseId){
        /*
            判断是否登陆，由汉槟提供
         */
        boolean isLogin = false;
        String redisKey = CacheNameHelper.EP_COURSE_PREFIX_GET_COURSEINFO_LIST;
        String redisItem = null;
        if (!isLogin){
            redisItem = String.format(CacheNameHelper.EP_COURSE_PREFIX_COURSE_ID,courseId);
            Object object = redisUtil.hget(redisKey,redisItem);
            if (object != null)
                return ResultVO.success(object);
        }
        EpCourse course = courseService.getByCourseId(courseId);
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        courseInfoVO.setCourse(course);
        courseInfoVO.setLogin(false);
        courseInfoVO.setPay(false);
        courseInfoVO.setScope(0); //从何亮获取
        courseInfoVO.setAuthor(null); //从汉槟获取
        courseInfoVO.setChapters();
        return ResultVO.success();
    }


    @ApiOperation(value = "清除缓存")
    @GetMapping("course/clear")
    ResultVO clear(){
        redisUtil.delFuz(CacheNameHelper.EP_COURSE_PREFIX);
        return ResultVO.success();
    }


}

