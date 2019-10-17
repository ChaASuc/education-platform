package cn.ep.mapper;

import cn.ep.bean.EpCheck;
import cn.ep.bean.EpCheckExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EpCheckMapper {
    int countByExample(EpCheckExample example);

    int deleteByExample(EpCheckExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EpCheck record);

    int insertSelective(EpCheck record);

    List<EpCheck> selectByExample(EpCheckExample example);

    EpCheck selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EpCheck record, @Param("example") EpCheckExample example);

    int updateByExample(@Param("record") EpCheck record, @Param("example") EpCheckExample example);

    int updateByPrimaryKeySelective(EpCheck record);

    int updateByPrimaryKey(EpCheck record);
}