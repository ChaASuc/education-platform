package cn.ep.service.impl;

import cn.ep.bean.EpRole;
import cn.ep.bean.EpRoleExample;
import cn.ep.bean.EpUserRole;
import cn.ep.bean.EpUserRoleExample;
import cn.ep.config.PageConfig;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpRoleMapper;
import cn.ep.mapper.EpUserRoleMapper;
import cn.ep.service.EpRoleService;
import cn.ep.utils.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
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

    @Transactional
    @Override
    public void insertUserRole(Long userId, Long roleId) {
        EpUserRoleExample epUserRoleExample = new EpUserRoleExample();
        epUserRoleExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andRoleIdEqualTo(roleId);
        List<EpUserRole> epUserRoles = epUserRoleMapper.selectByExample(epUserRoleExample);
        if (CollectionUtils.isEmpty(epUserRoles) && epUserRoles.size() == 0) {
            long id = idWorker.nextId();
            EpUserRole epUserRole = new EpUserRole();
            epUserRole.setuRId(id);
            epUserRole.setUserId(userId);
            epUserRole.setRoleId(roleId);
            boolean success = epUserRoleMapper.insertSelective(epUserRole) > 0 ? true : false;
            if (!success) {
                throw new GlobalException(GlobalEnum.OPERATION_ERROR, "提升角色失败");
            }
        }else {
            EpUserRole epUserRole = new EpUserRole();
            epUserRole.setDeleted(false);
            boolean success = epUserRoleMapper.updateByExampleSelective(epUserRole, epUserRoleExample) > 0 ? true : false;
            if (!success) {
                throw new GlobalException(GlobalEnum.OPERATION_ERROR, "提升角色失败");
            }
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

    @Override
    @Transactional
    public void updateUserRole(Long userId, Long roleId) {
        EpUserRoleExample epUserRoleExample = new EpUserRoleExample();
        epUserRoleExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andRoleIdEqualTo(roleId);
        EpUserRole epUserRole = new EpUserRole();
        epUserRole.setDeleted(true);
        boolean success = epUserRoleMapper.updateByExampleSelective(epUserRole, epUserRoleExample) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "降级失败");
        }
    }

    @Override
    public List<EpRole> getList() {
        EpRoleExample epRoleExample = new EpRoleExample();
        epRoleExample.createCriteria()
                .andDeletedEqualTo(false);
        List<EpRole> epRoles = epRoleMapper.selectByExample(epRoleExample);
        return epRoles;
    }
}
