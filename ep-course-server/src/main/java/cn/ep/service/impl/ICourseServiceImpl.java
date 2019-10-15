package cn.ep.service.impl;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpCourse;
import cn.ep.bean.EpCourseExample;
import cn.ep.bean.EpWatchRecord;
import cn.ep.courseenum.ChapterEnum;
import cn.ep.courseenum.CourseEnum;
import cn.ep.courseenum.WatchRecordEnum;
import cn.ep.mapper.EpCourseMapper;
import cn.ep.service.IChapterService;
import cn.ep.service.ICourseService;
import cn.ep.service.IWatchRecordService;
import cn.ep.utils.IdWorker;
import cn.ep.vo.ChapterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean insert(EpCourse epCourseKind) {
        epCourseKind.setId(idWorker.nextId());
        System.out.println(epCourseKind);
        return courseMapper.insertSelective(epCourseKind) > 0;
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
        course.setStatus(CourseEnum.UNCHECKED_STATUS.getValue());
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
    public Map<EpChapter, List<ChapterVO>> getCourseInfoVOByUserIdAndCourseIdAndStatusAndLogin(int userId, long courseId, int value, boolean isLogin) {
        Map<EpChapter,List<EpChapter>> chapterListMap =
                chapterService.getListByCourseIdAndStatus(courseId
                        , ChapterEnum.VALID_STATUS.getValue());
        Map<EpChapter,List<ChapterVO>> chapterVOListMap = new HashMap<>(chapterListMap.size());
        for (Map.Entry<EpChapter, List<EpChapter>> chapterListEntry :
                chapterListMap.entrySet()) {
            List<ChapterVO> chapterVOList = new ArrayList<>();
            List<EpWatchRecord> records = null;
            if (!isLogin)
                records = recordService.selectByUserIdAndCourseIdAndStatus(userId,courseId, WatchRecordEnum.VALID_STATUS.getValue());
            for (EpChapter c :
                    chapterListEntry.getValue()) {
                ChapterVO chapterVO = new ChapterVO();
                chapterVO.setChapter(c);
                if (!isLogin){
                    for (EpWatchRecord r :
                            records) {
                        if (r.getChapterId() == c.getId())
                            chapterVO.setRecord(r);
                    }
                }
                chapterVOList.add(chapterVO);
            }
            chapterVOListMap.put(chapterListEntry.getKey(),chapterVOList);
        }
        return chapterVOListMap;
    }
}
