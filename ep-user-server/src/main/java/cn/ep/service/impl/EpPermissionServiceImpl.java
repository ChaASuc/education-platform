package cn.ep.service.impl;

import cn.ep.bean.EpPermission;
import cn.ep.bean.EpPermissionExample;
import cn.ep.bean.EpRole;
import cn.ep.bean.EpRoleExample;
import cn.ep.config.PageConfig;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpPermissionMapper;
import cn.ep.mapper.EpRoleMapper;
import cn.ep.mapper.EpUserRoleMapper;
import cn.ep.service.EpPermissionService;
import cn.ep.service.EpRoleService;
import cn.ep.utils.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class EpPermissionServiceImpl implements EpPermissionService {

    @Autowired
    private EpPermissionMapper epPermissionMapper;

    @Autowired
    private EpUserRoleMapper epUserRoleMapper;

    @Autowired
    private PageConfig pageConfig;

    @Autowired
    private IdWorker idWorker;

    @Override
    @Transactional
    public void insert(EpPermission permission) {
        permission.setPermId(idWorker.nextId());
        boolean success = epPermissionMapper.insertSelective(permission) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "权限创建");
        }
    }

    @Override
    @Transactional
    public void update(EpPermission permission) {
        boolean success = epPermissionMapper.updateByPrimaryKeySelective(permission) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "权限更新");
        }
    }

    @Override
    public PageInfo<EpPermission> selectByDeptIdAndNum(Long deptId, Integer num) {
        PageHelper.startPage(num, pageConfig.getPageSize());
        EpPermissionExample example = new EpPermissionExample();
        example.createCriteria()
                .andDeletedEqualTo(false)
                .andDeptIdEqualTo(deptId);
        List<EpPermission> epPermissions = epPermissionMapper.selectByExample(example);
        PageInfo<EpPermission> epPermissionPageInfo = new PageInfo<>(epPermissions);
        return epPermissionPageInfo;
    }

    @Override
    public PageInfo<EpPermission> selectByRoleIdAndNum(Long roleId, Integer num) {
        PageHelper.startPage(num, pageConfig.getPageSize());

        List<EpPermission> epPermissions = epPermissionMapper.selectByRoleId(roleId);
        PageInfo<EpPermission> epPermissionPageInfo = new PageInfo<>(epPermissions);
        return epPermissionPageInfo;
    }
}
