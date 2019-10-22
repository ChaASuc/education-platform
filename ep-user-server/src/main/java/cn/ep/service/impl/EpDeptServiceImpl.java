package cn.ep.service.impl;

import cn.ep.bean.EpDept;
import cn.ep.bean.EpDeptExample;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpDeptMapper;
import cn.ep.service.EpDeptService;
import cn.ep.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/22
 * @Description
 * @Since 1.0.0
 */
@Service
public class EpDeptServiceImpl implements EpDeptService {

    @Autowired
    private EpDeptMapper epDeptMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    @Transactional
    public void insert(EpDept epDept) {
        epDept.setDeptId(idWorker.nextId());
        boolean success = epDeptMapper.insertSelective(epDept) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "部门创建失败");
        }
    }

    @Override
    @Transactional
    public void update(EpDept epDept) {
        boolean success = epDeptMapper.updateByPrimaryKey(epDept) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "部门创建失败");
        }
    }

    @Override
    public List<EpDept> getAll() {
        EpDeptExample epDeptExample = new EpDeptExample();
        epDeptExample.createCriteria()
                .andDeletedEqualTo(false);
        List<EpDept> epDepts = epDeptMapper.selectByExample(epDeptExample);
        return epDepts;
    }
}
