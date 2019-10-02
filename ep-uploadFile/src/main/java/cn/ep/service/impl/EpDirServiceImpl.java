package cn.ep.service.impl;

import cn.ep.bean.EpDir;
import cn.ep.bean.EpDirExample;
import cn.ep.dto.EpDirDto;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpDirMapper;
import cn.ep.service.EpDirService;
import cn.ep.service.UploadService;
import cn.ep.utils.IdWorker;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

    @Autowired
    private UploadService uploadService;

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

        uploadService.deleteByDirId(dirId);
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
    public List<EpDirDto> selectAll() {
        // 升序排列
        EpDirExample epDirExample = new EpDirExample();
        epDirExample.setOrderByClause("DIR_PARENT_ID ASC");
        List<EpDir> epDirs = epDirMapper.selectByExample(epDirExample);

        // 判空
        if (CollectionUtils.isEmpty(epDirs)) {
            return null;
        }

        // 一级目录文件夹集合
        List<EpDirDto> epDirDtosHeads = new ArrayList<>();
        Multimap<Long, EpDirDto> multimap = ArrayListMultimap.create();

        // 键可以重复的map数据结构，这样在查询特定键时可以返回多个值
        for (EpDir epDir:
             epDirs) {
            EpDirDto epDirDto = getEpDirDto(epDir);
            multimap.put(epDir.getDirParentId(), epDirDto);
            // 判断是否一级目录文件夹
            if (epDir.getDirParentId() == (0)) {
                epDirDtosHeads.add(epDirDto);
            }
        }

        // 多级树形目录
        transAllDirTree(epDirDtosHeads, multimap);

        return epDirDtosHeads;
    }


    private void transAllDirTree(List<EpDirDto> epDirDtosHeads, Multimap<Long, EpDirDto> multimap) {
        for (EpDirDto epDirDto :
                epDirDtosHeads) {
            // 获取一级文件夹下子目录
            List<EpDirDto> epDirDtos = (List<EpDirDto>) multimap.get(epDirDto.getDirId());
            // 判空，是否存在子目录，表示是不是最后文件夹
            if (!CollectionUtils.isEmpty(epDirDtos)) {
                // 有子目录，添加
                epDirDto.setEpDirDtos(epDirDtos);
                // 遍历子目录，递归
                transAllDirTree(epDirDtos, multimap);
            }
        }
    }

    @Override
    public EpDir selectById(Long dirId) {
        return epDirMapper.selectByPrimaryKey(dirId);
    }

    private EpDirDto getEpDirDto(EpDir epDir) {
        // 创建带目录的文件夹实体类
        EpDirDto epDirDto = new EpDirDto();
        BeanUtils.copyProperties(epDir, epDirDto);
        List<EpDirDto> epDirDtos = new ArrayList<>();
        epDirDto.setEpDirDtos(epDirDtos);
        return epDirDto;
    }
}
