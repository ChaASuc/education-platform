package cn.ep.service;

import cn.ep.bean.EpRole;
import com.github.pagehelper.PageInfo;

/**
 * @Author deschen
 * @Create 2019/10/22
 * @Description
 * @Since 1.0.0
 */
public interface EpRoleService {

    /**
     * 添加角色
     * @param role
     */
    void insert(EpRole role);

    /**
     * 更新或逻辑删除角色
     * @param role
     */
    void update(EpRole role);

    /**
     * 根据部门id和页码获取角色
     * @param deptId
     * @param num
     * @return
     */
    PageInfo<EpRole> selectByDeptIdAndNum(Long deptId, Integer num);

    /**
     * 根据用户id和页码获取角色
     * @param userId
     * @param num
     * @return
     */
    PageInfo<EpRole> selectByUserIdAndNum(Long userId, Integer num);
}
