package cn.ep.service.impl;

import cn.ep.bean.*;
import cn.ep.courseenum.ChapterEnum;
import cn.ep.courseenum.CheckEnum;
import cn.ep.courseenum.CourseEnum;
import cn.ep.courseenum.WatchRecordEnum;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpCourseMapper;
import cn.ep.service.IChapterService;
import cn.ep.service.ICheckService;
import cn.ep.service.ICourseService;
import cn.ep.service.IWatchRecordService;
import cn.ep.utils.IdWorker;
import cn.ep.vo.ChapterVO;
import cn.ep.vo.VerseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ICourseServiceImpl implements ICourseService {

    @Autowired
    private EpCourseMapper courseMapper;
    @Autowired
    private IChapterService chapterService;
    @Autowired
    private IWatchRecordService recordService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ICheckService checkService;

    @Override
    public List<EpCourse> getListByEpCourse(EpCourse epCourse, String orderBy) {
        EpCourseExample courseExample = new EpCourseExample();
        EpCourseExample.Criteria criteria = courseExample.createCriteria();
        if (epCourse.getCourseName() != null)
            criteria.andCourseNameEqualTo(epCourse.getCourseName());
        if (epCourse.getFree() != null)
            criteria.andFreeEqualTo(epCourse.getFree());
        if (epCourse.getCreateTime() != null)
            criteria.andCreateTimeEqualTo(epCourse.getCreateTime());
        if (epCourse.getGoal() != null)
            criteria.andGoalEqualTo(epCourse.getGoal());
        if (epCourse.getId() != null)
            criteria.andIdEqualTo(epCourse.getId());
        if (epCourse.getKindId() != null)
            criteria.andKindIdEqualTo(epCourse.getKindId());
        if (epCourse.getUserId() != null)
            criteria.andUserIdEqualTo(epCourse.getUserId());
        if (epCourse.getOpenTime() != null)
            criteria.andOpenTimeEqualTo(epCourse.getOpenTime());
        if (epCourse.getOverview() != null)
            criteria.andOverviewEqualTo(epCourse.getOverview());
        if (epCourse.getPrice() != null)
            criteria.andPriceEqualTo(epCourse.getPrice());
        if (epCourse.getStatus() != null)
            criteria.andStatusEqualTo(epCourse.getStatus());
        if (epCourse.getUpdateTime() != null)
            criteria.andUpdateTimeEqualTo(epCourse.getUpdateTime());
        if (epCourse.getWatchCount() != null)
            criteria.andWatchCountEqualTo(epCourse.getWatchCount());
        if (epCourse.getPictureUrl() != null)
            criteria.andPictureUrlEqualTo(epCourse.getPictureUrl());
        if (orderBy != null)
            courseExample.setOrderByClause(orderBy);
        return courseMapper.selectByExample(courseExample);
    }

    @Override
    public List<EpCourse> getListByKey(String key, int status) {
        return courseMapper.selectByKey(key,status);
    }
    @Override
    public boolean insert(@Validated  EpCourse epCourse) {
        //System.out.println(epCourse);
        return courseMapper.insertSelective(epCourse) > 0;
    }

    @Override
    public boolean updateById(EpCourse epCourse) {
        System.out.println(epCourse);
        return courseMapper.updateByPrimaryKeySelective(epCourse) > 0;
    }

    @Override
    public List<EpCourse> getListByTop(int top, int free, int status) {
        return courseMapper.selectByTop(top,free,status);
    }

    @Override
    public List<EpCourse> getListByKindIdAndFreeAndOrder(long kindId, int free, int order) {
        EpCourse course = new EpCourse();
        course.setStatus(CourseEnum.CHECK_PASS.getValue());
        course.setFree(free);
        course.setKindId(kindId);
        String orderString = null;
        if (order == 1){
            orderString = "create_time DESC";
        } else if (order == 2){
            orderString = "watch_count DESC";
        } else {
            orderString = "create_time DESC,watch_count DESC";
        }
        return getListByEpCourse(course,orderString);
    }

    @Override
    public EpCourse getByCourseId(@NotNull long courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public  List<ChapterVO> getCourseInfoVOByUserIdAndCourseIdAndStatusAndLogin(int userId, long courseId, int value, boolean isLogin) {
        Map<EpChapter,List<EpChapter>> chapterListMap =
                chapterService.getListByCourseIdAndStatus(courseId
                        , ChapterEnum.VALID_STATUS.getValue());
        //一个课程的所有（章与节）VO
        List<ChapterVO> chapterVOList = new ArrayList<>(chapterListMap.size());

       // Map<EpChapter,List<ChapterVO>> chapterVOListMap = new HashMap<>(chapterListMap.size());
        for (Map.Entry<EpChapter, List<EpChapter>> chapterListEntry :
                chapterListMap.entrySet()) {
            //（章与节）VO
            ChapterVO chapterVO = new ChapterVO();
            chapterVO.setChapter(chapterListEntry.getKey());

            //一个章的所有（节与观看记录）VO
            List<VerseVO> verseVOList = new ArrayList<>();
            List<EpWatchRecord> records = null;

            if (isLogin)  //如果登陆了 ，获取该用户该课程的所有观看记录
                records = recordService.selectByUserIdAndCourseIdAndStatus(userId,courseId, WatchRecordEnum.VALID_STATUS.getValue());

            for (EpChapter verse :
                    chapterListEntry.getValue()) {
                VerseVO verseVO = new VerseVO();
                verseVO.setVerse(verse);

                if (isLogin){  //因为没有登陆，records等于null
                    for (EpWatchRecord r :
                            records) {
                        if (r.getChapterId() == verse.getId())
                            verseVO.setRecord(r);
                    }
                }
                verseVOList.add(verseVO);
            }
            chapterVO.setVerseVOS(verseVOList);
            chapterVOList.add(chapterVO);
        }
        return chapterVOList;
    }

    @Override
    @Transactional
    public boolean insertAndSendCheck(EpCourse course) {
        course.setId(idWorker.nextId());
        // todo 获取当前登陆人的id
        long userId = 1L;
        course.setUserId(userId);
        course.setStatus(CourseEnum.UNCHECKED_STATUS.getValue());
        if (!insert(course))
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"增加课程失败");
        EpCheck check = new EpCheck();
        // todo 审核人id从汉槟随机获取管理员id
        long whoId = 1L;
        check.setWho(whoId);
        check.setStatus(CheckEnum.UNCHECKED_STATUS.getValue());
        check.setBelongId(course.getId());
        check.setBelong(CheckEnum.CHECK_COURSE.getValue());
        if (!checkService.insert(check))
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"增加课程失败");
        System.out.println(check);
        return true;
    }

    @Override
    public List<EpCourse> getListByUserIdAndStatusNotEqualTo(long userId, int status) {
        EpCourseExample courseExample = new EpCourseExample();
        EpCourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andStatusNotEqualTo(status);
        criteria.andUserIdEqualTo(userId);
        return courseMapper.selectByExample(courseExample);
    }
}
