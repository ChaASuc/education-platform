package cn.ep.service.impl;

import cn.ep.bean.EpUser;
import cn.ep.bean.EpUserExample;
import cn.ep.client.ProductClient;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpUserMapper;
import cn.ep.service.EpUserService;
import cn.ep.utils.IdWorker;
import cn.ep.utils.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/7
 * @Description
 * @Since 1.0.0
 */
@Slf4j
@Service
public class EpUserServiceImpl implements EpUserService {

    @Autowired
    private EpUserMapper epUserMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ProductClient productClient;


    @Override
    @Transactional
    public void insert(EpUser epUser) {
        long id = idWorker.nextId();
        epUser.setUserId(id);
        boolean success = epUserMapper.insertSelective(epUser) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "创建用户失败");
        }
    }

    @Override
    @Transactional
    public void update(EpUser epUser) {
        boolean success = epUserMapper.updateByPrimaryKeySelective(epUser) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "更新用户失败");
        }
        ResultVO resultVO = productClient.updateListByCid(1);
        if (resultVO.equals(GlobalEnum.SUCCESS.getCode())) {
            System.out.println("xxx");
        }
    }

    @Override
    public boolean checkUser(String account, Integer type) {
        EpUserExample epUserExample = new EpUserExample();
        EpUserExample.Criteria criteria = epUserExample.createCriteria().andDeletedEqualTo(false);
        //判断校验数据的类型
        switch (type) {
            case 1:
                criteria.andUserNameEqualTo(account);
                break;
            case 2:
                criteria.andUserPhoneEqualTo(account);
                break;
            default:
                throw new GlobalException(GlobalEnum.INVALID_PARAM);
        }

        return epUserMapper.selectByExample(epUserExample).size() == 1 ? true : false;

    }

    @Override
    public EpUser get(String username, String pwd) {
        EpUserExample epUserExample = new EpUserExample();
        epUserExample.createCriteria()
                .andUserNameEqualTo(username)
                .andUserPwdEqualTo(pwd)
                .andDeletedEqualTo(false);
        List<EpUser> epUsers = epUserMapper.selectByExample(epUserExample);
        if (epUsers.size() == 1) {
            return epUsers.get(0);
        }


        throw new GlobalException(GlobalEnum.EXIST_ERROR, "用户");
    }
}
