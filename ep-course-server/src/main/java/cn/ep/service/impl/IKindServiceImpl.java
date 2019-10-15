package cn.ep.service.impl;

import cn.ep.annotation.CanLook;
import cn.ep.bean.EpCheck;
import cn.ep.bean.EpCourseKind;
import cn.ep.bean.EpCourseKindExample;
import cn.ep.courseenum.CheckEnum;
import cn.ep.courseenum.CourseKindEnum;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpCourseKindMapper;
import cn.ep.service.ICheckService;
import cn.ep.service.IKindService;
import cn.ep.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

/**
* @author
* @description 只负责逻辑处理
* @date 15:03 2019-10-11
* @modified 15:03 2019-10-11
*/
@Service
public class IKindServiceImpl implements IKindService {

    @Autowired
    private EpCourseKindMapper kindMapper;

    @Autowired
    private ICheckService checkService;
    @Autowired
    private IdWorker idWorker;

    @Override
    public Map<EpCourseKind, List<EpCourseKind>> getListByStatus(int status) {
        EpCourseKindExample kindExample = new EpCourseKindExample();
        EpCourseKindExample.Criteria criteria = kindExample.createCriteria();
        criteria.andStatusEqualTo(status);
        List<EpCourseKind> kindList = kindMapper.selectByExample(kindExample);
        Map<EpCourseKind, List<EpCourseKind>> map = new HashMap<>();
        Map<Long, EpCourseKind> root = new LinkedHashMap<>();
        for (EpCourseKind kind :
                kindList) {
            if (kind.getRoot() == 0){
                root.put(kind.getId(),kind);
                map.put(kind,new ArrayList<>());
            }
        }
        for (EpCourseKind kind :
                kindList) {
            if (kind.getRoot() != 0){
                map.get(root.get(kind.getRoot())).add(kind);
            }
        }
        return map;
    }

    @Override
    //@CanLook
    public List<EpCourseKind> getListByRootAndStatus(int status, long root) {
        EpCourseKindExample kindExample = new EpCourseKindExample();
        EpCourseKindExample.Criteria criteria = kindExample.createCriteria();
        criteria.andStatusEqualTo(status).andRootEqualTo(root);
        List<EpCourseKind> kindList = kindMapper.selectByExample(kindExample);
        return kindList;
    }

    @Override
    public boolean insert(@Validated EpCourseKind epCourseKind) {
        return kindMapper.insertSelective(epCourseKind) > 0;
    }

    @Override
    public boolean updateById(EpCourseKind epCourseKind) {
        System.out.println(epCourseKind);
        return kindMapper.updateByPrimaryKeySelective(epCourseKind) > 0;
    }

    public List<EpCourseKind> getSubKindList(){
        EpCourseKindExample kindExample = new EpCourseKindExample();
        EpCourseKindExample.Criteria criteria = kindExample.createCriteria();
        criteria.andRootNotEqualTo(0L).andStatusEqualTo(1);
        kindExample.setOrderByClause("search_count");
        return  kindMapper.selectByExample(kindExample);
    }

    @Override
    @Transactional
    public boolean insertAndSendCheck(EpCourseKind epCourseKind) {

        epCourseKind.setId(idWorker.nextId());
        epCourseKind.setStatus(CourseKindEnum.INVALID_STATUS.getValue());
        System.out.println(epCourseKind);
        if (!insert(epCourseKind))
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"插入失败");
        EpCheck check = new EpCheck();
        check.setWho(1L);//审核人id从汉槟随机获取管理员id
        check.setStatus(CheckEnum.UNCHECKED_STATUS.getValue());
        check.setBelongId(epCourseKind.getId());
        check.setBelong(CheckEnum.CHECK_COURSE_KIND.getValue());
        if (!checkService.insert(check))
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"插入失败");
        return true;
    }

}
