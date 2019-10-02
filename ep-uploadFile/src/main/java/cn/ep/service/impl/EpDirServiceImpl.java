package cn.ep.service.impl;

import cn.ep.bean.EpDir;
import cn.ep.bean.EpDirExample;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpDirMapper;
import cn.ep.service.EpDirService;
import cn.ep.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/2
 * @Description
 * @Since 1.0.0
 */
@Slf4j
@Service
public class EpDirServiceImpl implements EpDirService {

    @Autowired
    private EpDirMapper epDirMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    @Transactional
    public void insert(EpDir dir) {
        dir.setDirId(idWorker.nextId());
        boolean success = epDirMapper.insertSelective(dir) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "文件夹创建失败");
        }
    }

    @Override
    @Transactional
    public void update(EpDir dir) {
        boolean success = epDirMapper.updateByPrimaryKeySelective(dir) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "文件夹更新失败");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long dirId) {
        boolean success = epDirMapper.deleteByPrimaryKey(dirId) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "文件夹删除失败");
        }
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> dirIds) {
        EpDirExample epDirExample = new EpDirExample();
        epDirExample.createCriteria()
                .andDirIdIn(dirIds);
        boolean success = epDirMapper.deleteByExample(epDirExample) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "文件夹批量删除失败");
        }
    }

    @Override
    public List<EpDir> selectAll() {
        return epDirMapper.selectByExample(null);
    }

    @Override
    public EpDir selectById(Long dirId) {
        return epDirMapper.selectByPrimaryKey(dirId);
    }
}
