package cn.ep.service.impl;

import cn.ep.bean.EpRole;
import cn.ep.bean.EpRoleExample;
import cn.ep.bean.EpUser;
import cn.ep.bean.EpUserExample;
import cn.ep.config.PageConfig;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpRoleMapper;
import cn.ep.mapper.EpUserRoleMapper;
import cn.ep.service.EpRoleService;
import cn.ep.utils.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import io.netty.util.internal.UnstableApi;
import org.springframework.beans.factory.FactoryBean;
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
public class EpRoleServiceImpl implements EpRoleService {

    @Autowired
    private EpRoleMapper epRoleMapper;

    @Autowired
    private EpUserRoleMapper epUserRoleMapper;

    @Autowired
    private PageConfig pageConfig;

    @Autowired
    private IdWorker idWorker;

    @Override
    @Transactional
    public void insert(EpRole role) {
        role.setRoleId(idWorker.nextId());
        boolean success = epRoleMapper.insertSelective(role) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "角色创建");
        }
    }

    @Override
    @Transactional
    public void update(EpRole role) {
        boolean success = epRoleMapper.updateByPrimaryKeySelective(role) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "角色更新");
        }
    }

    @Override
    public PageInfo<EpRole> selectByDeptIdAndNum(Long deptId, Integer num) {
        PageHelper.startPage(num, pageConfig.getPageSize());
        EpRoleExample epRoleExample = new EpRoleExample();
        epRoleExample.createCriteria()
                .andDeletedEqualTo(false)
                .andDeptIdEqualTo(deptId);
        List<EpRole> epRoles = epRoleMapper.selectByExample(epRoleExample);
        PageInfo<EpRole> epRolePageInfo = new PageInfo<>(epRoles);
        return epRolePageInfo;
    }

    @Override
    public PageInfo<EpRole> selectByUserIdAndNum(Long userId, Integer num) {
        PageHelper.startPage(num, pageConfig.getPageSize());

        List<EpRole> epRoles = epRoleMapper.selectByUserId(userId);
        PageInfo<EpRole> epRolePageInfo = new PageInfo<>(epRoles);
        return epRolePageInfo;
    }
}
