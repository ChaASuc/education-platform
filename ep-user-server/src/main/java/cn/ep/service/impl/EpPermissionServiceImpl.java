package cn.ep.service.impl;

import cn.ep.bean.*;
import cn.ep.config.PageConfig;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpPermissionMapper;
import cn.ep.mapper.EpRolePermissionMapper;
import cn.ep.mapper.EpUserRoleMapper;
import cn.ep.service.EpPermissionService;
import cn.ep.utils.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

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

    @Autowired
    private EpRolePermissionMapper epRolePermissionMapper;

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

    @Override
    public List<EpPermissionDto> selectEpPermissonDto() {
        List<EpPermissionDto> epPermissionDtos = epPermissionMapper.selectEpPermissionDto();
        return epPermissionDtos;
    }

    @Override
    @Transactional
    public void updateRolePerm(Long permissionId, Long roleId, boolean b) {
        EpRolePermission rolePermission = new EpRolePermission();
        rolePermission.setDeleted(b);
        EpRolePermissionExample epRolePermissionExample = new EpRolePermissionExample();
        epRolePermissionExample.createCriteria()
                .andRoleIdEqualTo(roleId)
                .andPermIdEqualTo(permissionId);
        boolean success = epRolePermissionMapper.updateByExampleSelective(rolePermission, epRolePermissionExample) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "权限角色配置失败");
        }
    }

}
