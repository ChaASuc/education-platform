package cn.ep.mapper;

import cn.ep.bean.EpUserRole;
import cn.ep.bean.EpUserRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpUserRoleMapper {
    int countByExample(EpUserRoleExample example);

    int deleteByExample(EpUserRoleExample example);

    int deleteByPrimaryKey(Long uRId);

    int insert(EpUserRole record);

    int insertSelective(EpUserRole record);

    List<EpUserRole> selectByExample(EpUserRoleExample example);

    EpUserRole selectByPrimaryKey(Long uRId);

    int updateByExampleSelective(@Param("record") EpUserRole record, @Param("example") EpUserRoleExample example);

    int updateByExample(@Param("record") EpUserRole record, @Param("example") EpUserRoleExample example);

    int updateByPrimaryKeySelective(EpUserRole record);

    int updateByPrimaryKey(EpUserRole record);
}