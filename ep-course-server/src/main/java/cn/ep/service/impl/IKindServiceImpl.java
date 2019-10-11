package cn.ep.service.impl;

import cn.ep.bean.EpCourseKind;
import cn.ep.bean.EpCourseKindExample;
import cn.ep.mapper.EpCourseKindMapper;
import cn.ep.service.IKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<EpCourseKind, List<EpCourseKind>> getListByStatus(int status) {
        EpCourseKindExample kindExample = new EpCourseKindExample();
        EpCourseKindExample.Criteria criteria = kindExample.createCriteria();
        criteria.andStatusEqualTo(status);
        List<EpCourseKind> kindList = kindMapper.selectByExample(kindExample);
        Map<EpCourseKind, List<EpCourseKind>> map = new HashMap<>();
        Map<Long, EpCourseKind> root = new HashMap<>();
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
    public List<EpCourseKind> getListByRootAndStatus(int status, int root) {
        return null;
    }

    @Override
    public boolean insert(EpCourseKind epCourseKind) {
        return false;
    }

    @Override
    public boolean update(EpCourseKind epCourseKind) {
        return false;
    }

}
