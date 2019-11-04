package cn.ep.service;

import cn.ep.bean.EpPermission;
import com.github.pagehelper.PageInfo;
import org.hibernate.validator.constraints.EAN;

import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/22
 * @Description
 * @Since 1.0.0
 */
public interface EpPermissionService {

    /**
     * 添加权限
     * @param perm
     */
    void insert(EpPermission perm);

    /**
     * 更新或逻辑删除权限
     * @param perm
     */
    void update(EpPermission perm);

    /**
     * 根据部门id和页码获取权限
     * @param deptId
     * @param num
     * @return
     */
    PageInfo<EpPermission> selectByDeptIdAndNum(Long deptId, Integer num);

    /**
     * 根据用户id和页码获取权限
     *
     * @param roleId
     * @param num
     * @return
     */
    PageInfo<EpPermission> selectByRoleIdAndNum(Long roleId, Integer num);


//    List<String> getCommonPerm();
//
//    List<String>
}
