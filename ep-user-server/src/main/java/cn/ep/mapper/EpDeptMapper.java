package cn.ep.mapper;

import cn.ep.bean.EpDept;
import cn.ep.bean.EpDeptExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpDeptMapper {
    int countByExample(EpDeptExample example);

    int deleteByExample(EpDeptExample example);

    int deleteByPrimaryKey(Long deptId);

    int insert(EpDept record);

    int insertSelective(EpDept record);

    List<EpDept> selectByExample(EpDeptExample example);

    EpDept selectByPrimaryKey(Long deptId);

    int updateByExampleSelective(@Param("record") EpDept record, @Param("example") EpDeptExample example);

    int updateByExample(@Param("record") EpDept record, @Param("example") EpDeptExample example);

    int updateByPrimaryKeySelective(EpDept record);

    int updateByPrimaryKey(EpDept record);
}