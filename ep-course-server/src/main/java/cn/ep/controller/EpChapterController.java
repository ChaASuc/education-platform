package cn.ep.controller;

import cn.ep.annotation.CanAdd;
import cn.ep.annotation.IsLogin;
import cn.ep.bean.EpChapter;
import cn.ep.bean.EpWatchRecord;
import cn.ep.courseenum.RoleEnum;
import cn.ep.service.IChapterService;
import cn.ep.service.IWatchRecordService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "课程模块：章节接口")
@RequestMapping(value = "ep/course/chapter")
public class EpChapterController {

    @Autowired
    private IChapterService chapterService;
    @Autowired
    private IWatchRecordService recordService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "增加【章|节】，权限：教师，可单条可批量，单条也要json数组，",notes = "开发人员已测试")
    @ApiImplicitParam(name="chapters",value = "可单可批量(json数组传输)：chapterName、courseId必填；duration，如果此记录为章，可填可不填，如果为节，不填；intro，暂时无用；url，如果此纪录为章，不填，如果为节，必填；root，如果此纪录为章，则为零，如果为节，则为某章的id；free，如果课程收费，则指出这一节（对于节来说，章不填）是否收费（收费为1，免费（试看）为0），如果课程不收费，则此课程的所有章节都为零；其余的都不填",dataType = "EpChapter")
    @PostMapping("")
    @IsLogin
    @CanAdd(role = {RoleEnum.TEACHER})
    ResultVO multInsert(@RequestBody List<EpChapter> chapters){
        System.out.println(chapters);
        chapterService.multiplyInsertAndSendCheck(chapters);
        return  ResultVO.success();
    }

    @ApiOperation(value = "更新观看记录",notes = "开发人员已测试")
    @ApiImplicitParam(name="record",value = "观看记录实体类，userId、chapterId、courseId必传，watchTime看需求，其他不必",dataType = "EpWatchRecord")
    @PostMapping("/record")
    @IsLogin
    ResultVO updateWatchRecord(@RequestBody EpWatchRecord record){
        if (!recordService.update(record)){
            if (recordService.insert(record)){
                String redisKey = String.format(EpCourseController.
                        CacheNameHelper.EP_COURSE_PREFIX_COURSE_ID_AND_USER_ID
                        ,record.getCourseId(),record.getUserId());
                redisUtil.del(redisKey);
            }
        } else {
            String redisKey = String.format(EpCourseController.
                            CacheNameHelper.EP_COURSE_PREFIX_COURSE_ID_AND_USER_ID
                    ,record.getCourseId(),record.getUserId());
            redisUtil.del(redisKey);
        }
        return ResultVO.success();
    }
}
