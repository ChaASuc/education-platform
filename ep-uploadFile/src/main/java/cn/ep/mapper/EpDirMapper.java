package cn.ep.mapper;

import cn.ep.bean.EpDir;
import cn.ep.bean.EpDirExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpDirMapper {
    int countByExample(EpDirExample example);

    int deleteByExample(EpDirExample example);

    int deleteByPrimaryKey(Long dirId);

    int insert(EpDir record);

    int insertSelective(EpDir record);

    List<EpDir> selectByExample(EpDirExample example);

    EpDir selectByPrimaryKey(Long dirId);

    int updateByExampleSelective(@Param("record") EpDir record, @Param("example") EpDirExample example);

    int updateByExample(@Param("record") EpDir record, @Param("example") EpDirExample example);

    int updateByPrimaryKeySelective(EpDir record);

    int updateByPrimaryKey(EpDir record);
}