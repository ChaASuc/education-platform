package cn.ep.controller;

import cn.ep.annotation.CanAdd;
import cn.ep.annotation.CanLook;
import cn.ep.annotation.IsLogin;
import cn.ep.bean.*;
import cn.ep.courseenum.ChapterEnum;
import cn.ep.courseenum.CourseEnum;
import cn.ep.courseenum.RoleEnum;
import cn.ep.courseenum.WatchRecordEnum;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.service.IChapterService;
import cn.ep.service.ICourseService;
import cn.ep.service.ICourseUserService;
import cn.ep.service.IWatchRecordService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import cn.ep.vo.ChapterVO;
import cn.ep.vo.CourseInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@Api(description = "课程模块：课程接口")
@RequestMapping("ep/course")
public class EpCourseController {

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        //这是获取redis热点种类的key值
        public static final String EP_COURSE_KIND_PREFIX_KIND_ID =
                "ep_courseKind_prefix_kind_%s";
        public static final String EP_COURSE_KIND_PREFIX_GET_LIST_TOP =
                "ep_courseKind_prefix_getListTop";
        //---------------------------------------------------

        // ep_category_prefix_getByUserNicknameAnduserPwd_{用户名}_{密码}
        public static final String EP_COURSE_PREFIX_GET_LIST_BY_KEY_AND_PAGE =
                "ep_course_prefix_getListByKeyAndPage_%s_%s";

        public static final String EP_COURSE_PREFIX_GET_LIST_TOP_BY_FREE =
                "ep_course_prefix_getListTopByFree_%s";
        //ep_user_prefix_* 用于全部删除，避免缓存
        public static final String EP_COURSE_PREFIX = "ep_course_prefix_*";
        public static final String EP_COURSE_PREFIX_GET_RECOMMEND_LIST = "ep_course_prefix_get_recommend_list";
        public static final String EP_COURSE_PREFIX_GET_LIST_BY_KINDID_AND_FREE_ORDER_PAGE = "ep_course_prefix_get_list_by_kindid_and_free_order_page_%s_%s_%s_%s";
        public static final String EP_COURSE_PREFIX_GET_CAROUSEL_LIST = "ep_course_prefix_getCarouselList";
        public static final String EP_COURSE_PREFIX_GET_COURSEINFO = "ep_course_prefix_getCourseInfo";
        public static final String EP_COURSE_PREFIX_COURSE_ID = "ep_course_prefix_courseId_%s";
        public static final String EP_COURSE_PREFIX_COURSE_ID_AND_USER_ID = "ep_course_prefix_courseId_and_userId_%s_%s";
        public static final String EP_COURSE_PREFIX_GET_LIST_BY_CURRENT_USER_ID = "ep_course_prefix_getListByCurrentUserId";


        public static final String EP_COURSE_PREFIX_USER_ID = "ep_course_prefix_userId_%s";
        public static final String EP_COURSE_PREFIX_getSubscriptionList = "ep_course_prefix_getsubscriptionlist";
    }


    @Autowired
    private ICourseService courseService;
    @Autowired
    private ICourseUserService courseUserService;

    @Autowired
    private RedisUtil redisUtil;

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
        List<EpCourse> courses = courseService.getListByKey(key, CourseEnum.CHECK_PASS.getValue());
        PageInfo<EpCourse> info = new PageInfo<>(courses);
        redisUtil.set(key,info,1800, TimeUnit.SECONDS);
        return ResultVO.success(info);
    }

    private List<CourseInfoVO> CoursesToCourseInfoVOs(List<EpCourse> courses) {
        List<CourseInfoVO> courseInfoVOS = new ArrayList<>(21);
        for (EpCourse c :
                courses) {
            CourseInfoVO courseInfoVO = new CourseInfoVO();
            courseInfoVO.setCourse(c);
            // todo  这里从其他模块获取
            courseInfoVO.setAuthor(null);   //如果需要，跟用户模块获取
            courseInfoVO.setScope(0);   //如果需要，向评价模块获取
            courseInfoVO.setChapters(null);
            courseInfoVOS.add(courseInfoVO);
        }
        return courseInfoVOS;
    }

    @ApiOperation(value = "获取课程排行榜，20条记录，以时间+订阅为排行依据，非实时，次日更新", notes = "开发人员已测试")
    @ApiImplicitParam(name = "free", value = "查询是否免费:0为免费，1为收费", dataType = "String", paramType = "path")
    @GetMapping(value = "top/list/{free}")
    ResultVO getListTopByFree(@PathVariable int free){
        String redisKey = String.format(CacheNameHelper.EP_COURSE_PREFIX_GET_LIST_TOP_BY_FREE,free);
        Object object = redisUtil.get(redisKey);
        if (object != null)
            return ResultVO.success(object);
      //  System.out.println(free);
        List<EpCourse> courses = courseService.getListByTop(CourseEnum.CHECK_PASS.getValue(),free,20);
        List<CourseInfoVO> courseInfoVOS = CoursesToCourseInfoVOs(courses);
        redisUtil.set(redisKey,courseInfoVOS,1,TimeUnit.DAYS);
        return ResultVO.success(courseInfoVOS);
    }

    @ApiOperation(value = "获取首页轮播信息，4条记录，以时间+订阅为排行依据，非实时，次日更新", notes = "开发人员已测试")
    @GetMapping(value = "carouse/list")
    ResultVO getCarouselList(){
        String redisKey = CacheNameHelper.EP_COURSE_PREFIX_GET_CAROUSEL_LIST;
        Object object = redisUtil.get(redisKey);
        if (object != null)
            return ResultVO.success(object);
        //System.out.println("55");
        List<EpCourse> courses = courseService.getListByTop(CourseEnum.CHECK_PASS.getValue(),1,4);
        System.out.println(courses.size());
        redisUtil.set(redisKey,courses,1,TimeUnit.DAYS);
        return ResultVO.success(courses);
    }


    @ApiOperation(value = "获取课程推荐榜，8条记录，以时间+订阅+评分为排行依据，非实时，次日更新", notes = "开发人员已测试")
    @GetMapping(value = "recommend/list")
    ResultVO getRecommendList(){
        String redisKey = CacheNameHelper.EP_COURSE_PREFIX_GET_RECOMMEND_LIST;
        Object object = redisUtil.get(redisKey);
        /*if (object != null)
            return ResultVO.success(object);*/
        List<EpCourse> courses = courseService.getListByTop(CourseEnum.CHECK_PASS.getValue(),1,50);
        List<CourseInfoVO> courseInfoVOS = CoursesToCourseInfoVOs(courses);

        System.out.println(courses);
        courseInfoVOS.sort(Comparator.comparing(CourseInfoVO::getScope).reversed());
        courseInfoVOS = courseInfoVOS.subList(0,courseInfoVOS.size()<8?courseInfoVOS.size():8);
       // System.out.println(courseInfoVOS);
        redisUtil.set(redisKey,courseInfoVOS,1,TimeUnit.DAYS);
        return ResultVO.success(courseInfoVOS);
    }

    @ApiOperation(value = "获取某一种类课程信息，一页40条", notes = "开发人员已测试")
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
                ,kindId,free,order,page);
        Object object = redisUtil.get(redisKey);
        if (object != null)
            return ResultVO.success(object);
        PageHelper.startPage(page,40);
        List<EpCourse> courseList = courseService.getListByKindIdAndFreeAndOrder(kindId,free,order);
        redisUtil.set(redisKey,courseList);
        return ResultVO.success(courseList);
    }

    @ApiOperation(value = "获取通过课程id获取课程相关信息，课程作者，该课程信息，评分，章节信息【章与节&每个节的观看记录（如果登陆了）】，是否已订阅，是否登陆", notes = "开发人员已测试")
    @ApiImplicitParam(name = "courseId", value = "课程id", dataType = "long", paramType = "path")
    @GetMapping(value = "/{courseId}")
    ResultVO getCourseInfoByCourseId(@PathVariable long courseId){
        /*
            判断是否登陆，由汉槟提供
         */
        // todo  这里从其他模块获取
        boolean isLogin = false; //登陆为真
        int userId = 1;  //从用户模块获取
        String redisKey = CacheNameHelper.EP_COURSE_PREFIX_GET_COURSEINFO;
        String redisItem = null;
        if (!isLogin){
            redisItem = String.format(CacheNameHelper.EP_COURSE_PREFIX_COURSE_ID,courseId);
            Object object = redisUtil.hget(redisKey,redisItem);
            if (object != null)
                return ResultVO.success(object);
        } else {
            redisItem = String.format(CacheNameHelper.EP_COURSE_PREFIX_COURSE_ID_AND_USER_ID,courseId,userId);
            Object object = redisUtil.get(redisItem);
            if (object != null)
                return ResultVO.success(object);
        }
        EpCourse course = courseService.getByCourseId(courseId);
        if (course == null)
            return ResultVO.failure(GlobalEnum.PARAMS_ERROR,"课程不存在");
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        courseInfoVO.setCourse(course);
        courseInfoVO.setLogin(isLogin);
        //订阅包括付费订阅与免费订阅
        if (!isLogin)
            courseInfoVO.setSubscription(false);
        else
            courseInfoVO.setSubscription(courseUserService.getByUserIdAndCourseId(userId,courseId)!=null);
        // todo  这里从其他模块获取
        courseInfoVO.setScope(0); //从评论模块获取
        courseInfoVO.setAuthor(null); //从用户模块获取
        courseInfoVO.setChapters(
                courseService.getCourseInfoVOByUserIdAndCourseIdAndStatusAndLogin(
                        userId,courseId, WatchRecordEnum.VALID_STATUS.getValue(),isLogin));
        if (!isLogin)
            redisUtil.hset(redisKey,redisItem,courseInfoVO);
        else
            redisUtil.set(redisItem,courseInfoVO,1,TimeUnit.DAYS);
        return ResultVO.success(courseInfoVO);
    }

    @ApiOperation(value = "增加一个课程:教师权限", notes = "开发人员已测试")
    @ApiImplicitParam(name="course",value = "课程实体类:其中courseName、free（0免费1收费）、kindId、price必传，goal、overview、pictureUrl可选，但最好传，id、stutas不传", dataType = "EpCourse")
    @PostMapping("")
    @IsLogin
    @CanAdd(role = {RoleEnum.TEACHER})
    ResultVO insert(@RequestBody EpCourse course){
        if (!courseService.insertAndSendCheck(course))
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"添加课程失败");
        return ResultVO.success();
    }

    @ApiOperation(value = "获取当前用户所上传的课程,需要权限，只提供给教师角色",notes = "开发人员已测试")
    @GetMapping("/current/list")
    @IsLogin
    @CanLook(role = {RoleEnum.TEACHER})
    ResultVO getListByCurrentUserId(){
        //todo 从汉槟获取当前用户id
        long userId = 1L;
        String redisKey = CacheNameHelper.EP_COURSE_PREFIX_GET_LIST_BY_CURRENT_USER_ID;
        String redisItem = String.format(CacheNameHelper.EP_COURSE_PREFIX_USER_ID, userId);
        Object object = redisUtil.hget(redisKey,redisItem);
        System.out.println(object);
        if (object != null)
            return ResultVO.success(object);
        List<EpCourse> courseList = courseService.getListByUserIdAndStatusNotEqualTo(userId,CourseEnum.INVALID_STATUS.getValue());
        redisUtil.hset(redisKey,redisItem,courseList);
        return ResultVO.success(courseList);

    }

    @ApiOperation(value = "订阅课程",notes = "开发人员已测试")
    @ApiImplicitParam(name = "courseId", value = "课程id", dataType = "long", paramType = "path")
    @PostMapping("/subscription/{courseId}")
    @IsLogin
    ResultVO subscription(@PathVariable long courseId){
        //todo 从汉槟获取当前用户id
        long userId = 1L;
        courseUserService.subscription(courseId,userId);
        String redisKey = CacheNameHelper.EP_COURSE_PREFIX_getSubscriptionList;
        String redisItem = String.format(CacheNameHelper.EP_COURSE_PREFIX_USER_ID,userId);
        redisUtil.hdel(redisKey,redisItem);
        return ResultVO.success();
    }

    @ApiOperation(value = "获取当前用户订阅的所有课程", notes = "开发人员已测试")
    @GetMapping("/subscription/list")
    @IsLogin
    ResultVO getSubscriptionList(){

        long userId = 1L;
        String redisKey = CacheNameHelper.EP_COURSE_PREFIX_getSubscriptionList;
        String redisItem = String.format(CacheNameHelper.EP_COURSE_PREFIX_USER_ID,userId);
        Object object = redisUtil.hget(redisKey,redisItem);
        if (object != null)
            return ResultVO.success(object);
        //todo--------把业务逻辑下移到哪个服务？
        List<EpCourseUser> courseUserList = courseUserService.getListByUserId(userId);
        List<EpCourse> courseList = new ArrayList<>();

        if (courseUserList != null && courseUserList.size() != 0){
            for (EpCourseUser courseUser :
                    courseUserList) {
                courseList.add(courseService.getByCourseId(courseUser.getCourseId()));
            }
            redisUtil.hset(redisKey,redisItem,courseList);
        }
        System.out.println(courseUserList);
        return ResultVO.success(courseList);
    }
}

