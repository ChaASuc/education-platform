package cn.ep.mapper;

import cn.ep.bean.CourseScore;
import cn.ep.bean.CourseScoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseScoreMapper {
    int countByExample(CourseScoreExample example);

    int deleteByExample(CourseScoreExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CourseScore record);

    int insertSelective(CourseScore record);

    List<CourseScore> selectByExample(CourseScoreExample example);

    CourseScore selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CourseScore record, @Param("example") CourseScoreExample example);

    int updateByExample(@Param("record") CourseScore record, @Param("example") CourseScoreExample example);

    int updateByPrimaryKeySelective(CourseScore record);

    int updateByPrimaryKey(CourseScore record);
}