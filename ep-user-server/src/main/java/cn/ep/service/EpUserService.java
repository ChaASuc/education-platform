package cn.ep.service;

import cn.ep.bean.EpUser;

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
    EpUser getUserByAccountAndType(String userNickname, Integer type);

    /**
     * 根据用户名和密码查找用户
     * @param username
     * @param pwd
     * @return
     */
    EpUser get(String username, String pwd);

}
