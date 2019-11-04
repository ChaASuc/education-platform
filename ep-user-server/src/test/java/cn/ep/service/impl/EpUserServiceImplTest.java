package cn.ep.service.impl;

import cn.ep.bean.EpUser;
import cn.ep.service.EpUserService;
import cn.ep.utils.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class EpUserServiceImplTest extends TestUtil {

    @Autowired
    private EpUserService epUserService;

    @Test
    public void insert() {
        EpUser epUser = new EpUser();
        epUser.setUserName("厉害咯");
        epUser.setUserPwd("123456");
        epUserService.insert(epUser);
    }

    @Test
    public void update() {
    }

    @Test
    public void getUserByUserNicknameAndType() {
    }

    @Test
    public void selectByDeptId() {
    }

    @Test
    public void selectEpUserDetailByUserNickName() {
    }
}