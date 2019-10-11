package cn.ep.mapper;

import cn.ep.bean.EpCourse;
import cn.ep.bean.EpCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EpCourseMapper {
    int countByExample(EpCourseExample example);

    int deleteByExample(EpCourseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EpCourse record);

    int insertSelective(EpCourse record);

    List<EpCourse> selectByExample(EpCourseExample example);

    EpCourse selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EpCourse record, @Param("example") EpCourseExample example);

    int updateByExample(@Param("record") EpCourse record, @Param("example") EpCourseExample example);

    int updateByPrimaryKeySelective(EpCourse record);

    int updateByPrimaryKey(EpCourse record);

    List<EpCourse> selectByKey(@Param("keyString") String keyString, @Param("status") int status);
}