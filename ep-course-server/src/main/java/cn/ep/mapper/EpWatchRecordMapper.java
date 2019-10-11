package cn.ep.mapper;

import cn.ep.bean.EpWatchRecord;
import cn.ep.bean.EpWatchRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpWatchRecordMapper {
    int countByExample(EpWatchRecordExample example);

    int deleteByExample(EpWatchRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EpWatchRecord record);

    int insertSelective(EpWatchRecord record);

    List<EpWatchRecord> selectByExample(EpWatchRecordExample example);

    EpWatchRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EpWatchRecord record, @Param("example") EpWatchRecordExample example);

    int updateByExample(@Param("record") EpWatchRecord record, @Param("example") EpWatchRecordExample example);

    int updateByPrimaryKeySelective(EpWatchRecord record);

    int updateByPrimaryKey(EpWatchRecord record);
}