package cn.ep.service.impl;

import cn.ep.bean.EpCourseUser;
import cn.ep.bean.EpCourseUserExample;
import cn.ep.mapper.EpCourseUserMapper;
import cn.ep.service.ICourseUserService;
import cn.ep.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICourseUserServiceImpl implements ICourseUserService {

    @Autowired
    EpCourseUserMapper courseUserMapper;
    @Autowired
    IdWorker idWorker;

    @Override
    public boolean insert(EpCourseUser courseUser) {
        return courseUserMapper.insert(courseUser) > 0;
    }

    @Override
    public List<EpCourseUser> getListByCourseId(long courseId) {
        EpCourseUser condition = new EpCourseUser();
        condition.setUserId(courseId);
        return getListByCourseUser(condition);
    }

    @Override
    public List<EpCourseUser> getListByUserId(long userId) {
        EpCourseUser condition = new EpCourseUser();
        condition.setUserId(userId);
        return getListByCourseUser(condition);
    }

    @Override
    public List<EpCourseUser> getListByCourseUser(EpCourseUser courseUser) {
        EpCourseUserExample epCourseUserExample = new EpCourseUserExample();
        EpCourseUserExample.Criteria criteria = epCourseUserExample.createCriteria();
        if (courseUser.getCourseId() != null)
            criteria.andCourseIdEqualTo(courseUser.getCourseId());
        if (courseUser.getId() != null)
            criteria.andIdEqualTo(courseUser.getId());
        if (courseUser.getUserId() != null)
            criteria.andCourseIdEqualTo(courseUser.getCourseId());
        if (courseUser.getCreateTime() != null)
            criteria.andCreateTimeEqualTo(courseUser.getCreateTime());
        if (courseUser.getUpdateTime() != null)
            criteria.andUpdateTimeEqualTo(courseUser.getUpdateTime());
        return courseUserMapper.selectByExample(epCourseUserExample);
    }

    @Override
    public EpCourseUser getByUserIdAndCourseId(long courseId, long userId) {
        EpCourseUser condition = new EpCourseUser();
        condition.setCourseId(courseId);
        condition.setUserId(userId);
        List<EpCourseUser> courseUserList = getListByCourseUser(condition);
        if (courseUserList == null || courseUserList.size() == 0)
            return null;
        else
            return courseUserList.get(0);
    }
}
