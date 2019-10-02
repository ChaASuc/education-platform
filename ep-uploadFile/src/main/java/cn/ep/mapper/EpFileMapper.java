package cn.ep.mapper;

import cn.ep.bean.EpFile;
import cn.ep.bean.EpFileExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpFileMapper {
    int countByExample(EpFileExample example);

    int deleteByExample(EpFileExample example);

    int deleteByPrimaryKey(Long fileId);

    int insert(EpFile record);

    int insertSelective(EpFile record);

    List<EpFile> selectByExample(EpFileExample example);

    EpFile selectByPrimaryKey(Long fileId);

    int updateByExampleSelective(@Param("record") EpFile record, @Param("example") EpFileExample example);

    int updateByExample(@Param("record") EpFile record, @Param("example") EpFileExample example);

    int updateByPrimaryKeySelective(EpFile record);

    int updateByPrimaryKey(EpFile record);
}