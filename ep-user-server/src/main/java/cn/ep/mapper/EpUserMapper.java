package cn.ep.mapper;

import cn.ep.bean.EpUser;
//import cn.ep.bean.EpUserDetails;
import cn.ep.bean.EpUserDetails;
import cn.ep.bean.EpUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpUserMapper {
    int countByExample(EpUserExample example);

    int deleteByExample(EpUserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(EpUser record);

    int insertSelective(EpUser record);

    List<EpUser> selectByExample(EpUserExample example);

    EpUser selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") EpUser record, @Param("example") EpUserExample example);

    int updateByExample(@Param("record") EpUser record, @Param("example") EpUserExample example);

    int updateByPrimaryKeySelective(EpUser record);

    int updateByPrimaryKey(EpUser record);

    EpUserDetails selectByUsername(String username, Integer type);
}