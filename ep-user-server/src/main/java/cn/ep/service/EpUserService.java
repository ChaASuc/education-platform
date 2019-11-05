package cn.ep.service;

import cn.ep.bean.EpUser;
//import cn.ep.bean.EpUserDetails;
import cn.ep.bean.EpUserDetails;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/7
 * @Description
 * @Since 1.0.0
 */
public interface EpUserService {

    /**
     * 创建用户
     * @param epUser
     */
    void insert(EpUser epUser);

    /**
     * 更新和逻辑删除用户
     * @param epUser
     */
    void update(EpUser epUser);

    /**
     * 根据type类型用户名和手机号是否被注册
     * @param userNickname
     * @param type
     * @return
     */
    EpUser getUserByUserNicknameAndType(String userNickname, Integer type);

    /**
     * 根据部门id获取所有有效的用户
     * @param deptId
     * @param num
     * @return
     */
    PageInfo<EpUser> selectByDeptIdAndNum(Long deptId, Integer num);

    /**
     * 获取所有有效的用户
     * @return
     */
    List<EpUserDetails> selectEpUserDetails();


    /**
     * 根据用户名获取用户信息，用于UserDetail
     * @param userNickname
     * @param type
     * @return
     */
    EpUserDetails selectEpUserDetailByUserNickName(String userNickname, Integer type);

    EpUserDetails selectDetailsByUserName(String username);
}
