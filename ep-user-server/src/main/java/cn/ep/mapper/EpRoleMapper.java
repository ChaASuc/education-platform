package cn.ep.mapper;

import cn.ep.bean.EpRole;
import cn.ep.bean.EpRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpRoleMapper {
    int countByExample(EpRoleExample example);

    int deleteByExample(EpRoleExample example);

    int deleteByPrimaryKey(Long roleId);

    int insert(EpRole record);

    int insertSelective(EpRole record);

    List<EpRole> selectByExample(EpRoleExample example);

    List<EpRole> selectByUserId(Long userId);

    EpRole selectByPrimaryKey(Long roleId);

    int updateByExampleSelective(@Param("record") EpRole record, @Param("example") EpRoleExample example);

    int updateByExample(@Param("record") EpRole record, @Param("example") EpRoleExample example);

    int updateByPrimaryKeySelective(EpRole record);

    int updateByPrimaryKey(EpRole record);
}