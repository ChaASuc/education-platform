package cn.ep.service.impl;

import cn.ep.bean.EpCourseUser;
import cn.ep.bean.EpCourseUserExample;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
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
        courseUser.setId(idWorker.nextId());
        return courseUserMapper.insertSelective(courseUser) > 0;
    }

    @Override
    public List<EpCourseUser> getListByCourseId(long courseId) {
        EpCourseUser condition = new EpCourseUser();
        condition.setCourseId(courseId);
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
            criteria.andUserIdEqualTo(courseUser.getUserId());
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

    @Override
    public boolean subscription(long courseId,long userId) {
        if (getByUserIdAndCourseId(courseId,userId) != null)
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "不能重复订阅");
        boolean isfree = false;
        if (!isfree) {
            //todo 从订单模块获取当前课程是否购买成功 在service层调用其他模块提供的服务是否规范？
            boolean isPay = true;
            if (!isPay)
                throw new GlobalException(GlobalEnum.OPERATION_ERROR, "该课程为付费课程，请先购买");
        }
        EpCourseUser courseUser = new EpCourseUser();
        courseUser.setUserId(userId);
        courseUser.setCourseId(courseId);
        System.out.println(courseUser);
        if (!insert(courseUser))
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "订阅失败");
        return true;
    }
}
