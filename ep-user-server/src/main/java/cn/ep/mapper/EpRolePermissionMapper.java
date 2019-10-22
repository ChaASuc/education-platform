package cn.ep.mapper;

import cn.ep.bean.EpRolePermission;
import cn.ep.bean.EpRolePermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpRolePermissionMapper {
    int countByExample(EpRolePermissionExample example);

    int deleteByExample(EpRolePermissionExample example);

    int deleteByPrimaryKey(Long rPId);

    int insert(EpRolePermission record);

    int insertSelective(EpRolePermission record);

    List<EpRolePermission> selectByExample(EpRolePermissionExample example);

    EpRolePermission selectByPrimaryKey(Long rPId);

    int updateByExampleSelective(@Param("record") EpRolePermission record, @Param("example") EpRolePermissionExample example);

    int updateByExample(@Param("record") EpRolePermission record, @Param("example") EpRolePermissionExample example);

    int updateByPrimaryKeySelective(EpRolePermission record);

    int updateByPrimaryKey(EpRolePermission record);
}