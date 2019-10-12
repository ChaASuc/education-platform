package cn.ep.service.impl;

import cn.ep.bean.EpCourse;
import cn.ep.bean.EpCourseExample;
import cn.ep.mapper.EpCourseMapper;
import cn.ep.service.ICourseService;
import cn.ep.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICourseServiceImpl implements ICourseService {

    @Autowired
    private EpCourseMapper courseMapper;
    @Autowired
    private IdWorker idWorker;

    @Override
    public List<EpCourse> getListByEpCourse(EpCourse epCourse) {
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
}