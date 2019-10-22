package cn.ep.service.impl;

import cn.ep.bean.EpDept;
import cn.ep.bean.EpDeptDto;
import cn.ep.bean.EpDeptExample;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.EpDeptMapper;
import cn.ep.service.EpDeptService;
import cn.ep.utils.IdWorker;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sun.security.x509.X400Address;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/22
 * @Description
 * @Since 1.0.0
 */
@Service
public class EpDeptServiceImpl implements EpDeptService {

    @Autowired
    private EpDeptMapper epDeptMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    @Transactional
    public void insert(EpDept epDept) {
        epDept.setDeptId(idWorker.nextId());
        boolean success = epDeptMapper.insertSelective(epDept) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "部门创建失败");
        }
    }

    @Override
    @Transactional
    public void update(EpDept epDept) {
        boolean success = epDeptMapper.updateByPrimaryKeySelective(epDept) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "部门创建失败");
        }
    }

    @Override
    public List<EpDeptDto> selectAll() {
        // 升序排列
        EpDeptExample example = new EpDeptExample();
        example.setOrderByClause("DEPT_PARENT_ID ASC");
        example.createCriteria().andDeletedEqualTo(false);
        List<EpDept> epDepts = epDeptMapper.selectByExample(example);

        // 判空
        if (CollectionUtils.isEmpty(epDepts)) {
            return null;
        }

        // 一级目录文件夹集合
        List<EpDeptDto> epDeptDtosHeads = new ArrayList<>();
        Multimap<Long, EpDeptDto> multimap = ArrayListMultimap.create();

        // 键可以重复的map数据结构，这样在查询特定键时可以返回多个值
        for (EpDept epDept:
                epDepts) {
            EpDeptDto epDeptDto = getEpDeptDto(epDept);
            multimap.put(epDept.getDeptParentId(), epDeptDto);
            // 判断是否一级目录文件夹
            if (epDept.getDeptParentId() == 0) {
                epDeptDtosHeads.add(epDeptDto);
            }
        }

        // 多级树形目录
        transAllDirTree(epDeptDtosHeads, multimap);

        return epDeptDtosHeads;
    }



    private void transAllDirTree(List<EpDeptDto> epDeptDtosHeads, Multimap<Long, EpDeptDto> multimap) {
        for (EpDeptDto epDeptDto :
                epDeptDtosHeads) {
            // 获取一级文件夹下子目录
            List<EpDeptDto> epDeptDtos = (List<EpDeptDto>) multimap.get(epDeptDto.getDeptId());
            // 判空，是否存在子目录，表示是不是最后部门
            if (!CollectionUtils.isEmpty(epDeptDtos)) {
                // 有子目录，添加
                epDeptDto.setEpDeptDtos(epDeptDtos);
                // 遍历子目录，递归
                transAllDirTree(epDeptDtos, multimap);
            }
        }
    }

    private EpDeptDto getEpDeptDto(EpDept epDept) {
        // 创建带目录的部门实体类
        EpDeptDto epDeptDto = new EpDeptDto();
        BeanUtils.copyProperties(epDept, epDeptDto);
        List<EpDeptDto> epDeptDtos = new ArrayList<>();
        epDeptDto.setEpDeptDtos(epDeptDtos);
        return epDeptDto;
    }
}
