package cn.ep.service.impl;

import cn.ep.bean.EpCheck;
import cn.ep.bean.EpCheckExample;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpChapterMapper;
import cn.ep.mapper.EpCheckMapper;
import cn.ep.service.ICheckService;
import cn.ep.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ICheckServiceImpl implements ICheckService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private EpCheckMapper checkMapper;

    @Override
    public List<EpCheck> getListByEpCheck(EpCheck epCheck) {
        EpCheckExample checkExample = new EpCheckExample();
        EpCheckExample.Criteria criteria = checkExample.createCriteria();
        if (epCheck.getBelong() != null)
            criteria.andBelongEqualTo(epCheck.getBelong());
        if (epCheck.getBelongId() != null)
            criteria.andBelongIdEqualTo(epCheck.getBelongId());
        if (epCheck.getCreateTime() != null)
            criteria.andCreateTimeEqualTo(epCheck.getCreateTime());
        if (epCheck.getId() != null)
            criteria.andIdEqualTo(epCheck.getId());
        if (epCheck.getReason() != null)
            criteria.andReasonEqualTo(epCheck.getReason());
        if (epCheck.getStatus() != null)
            criteria.andStatusEqualTo(epCheck.getStatus());
        if (epCheck.getUpdateTime() != null)
            criteria.andUpdateTimeEqualTo(epCheck.getUpdateTime());
        if (epCheck.getWho() != null)
            criteria.andWhoEqualTo(epCheck.getWho());
        return checkMapper.selectByExample(checkExample);
    }

    @Override
    public List<EpCheck> getListByWhoAndBelongAndBelongId(long userId, int belong, long belongId) {
        EpCheck check = new EpCheck();
        check.setWho(userId);
        check.setBelong(belong);
        check.setBelongId(belongId);
        return getListByEpCheck(check);
    }


    @Override
    public List<EpCheck> getListByWhoAndBelongAndBelongIdAndStatus(long userId, int belong, long belongId, int status) {
        EpCheck check = new EpCheck();
        check.setWho(userId);
        check.setBelong(belong);
        check.setBelongId(belongId);
        check.setStatus(status);
        return getListByEpCheck(check);
    }

    @Override
    public boolean check(long id, int status) {
        EpCheck epCheck = getById(id);
        if (epCheck.getStatus() != 0)
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"不得多次审核!");
        epCheck.setStatus(status);
        if (!update(epCheck))
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"审核失败");
        return true;
    }

    @Override
    public boolean insert(EpCheck epCheck) {
        epCheck.setId(idWorker.nextId());
        return checkMapper.insert(epCheck) > 0;
    }

    @Override
    public boolean update(EpCheck epCheck) {
        return checkMapper.updateByPrimaryKeySelective(epCheck) > 0;
    }

    @Override
    public EpCheck getById(long id) {
        return checkMapper.selectByPrimaryKey(id);
    }
}
