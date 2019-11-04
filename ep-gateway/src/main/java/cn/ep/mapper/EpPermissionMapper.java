package cn.ep.mapper;//package cn.ep.mapper;

import cn.ep.bean.EpPermission;
import cn.ep.bean.EpPermissionExample;
import cn.ep.bean.EpUserDetialsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

//import cn.ep.bean.EpUserDetialsPermission;

public interface EpPermissionMapper {
    int countByExample(EpPermissionExample example);

    int deleteByExample(EpPermissionExample example);

    int deleteByPrimaryKey(Long permId);

    int insert(EpPermission record);

    int insertSelective(EpPermission record);

    List<EpPermission> selectByExample(EpPermissionExample example);

    List<EpPermission> selectByRoleId(Long roleId);

    EpPermission selectByPrimaryKey(Long permId);

    int updateByExampleSelective(@Param("record") EpPermission record, @Param("example") EpPermissionExample example);

    int updateByExample(@Param("record") EpPermission record, @Param("example") EpPermissionExample example);

    int updateByPrimaryKeySelective(EpPermission record);

    int updateByPrimaryKey(EpPermission record);

    Set<EpUserDetialsPermission> selectByRoleName(String commonRoleName);
}