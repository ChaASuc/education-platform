package cn.ep.mapper;

import cn.ep.bean.EpCourseKind;
import cn.ep.bean.EpCourseKindExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EpCourseKindMapper {
    int countByExample(EpCourseKindExample example);

    int deleteByExample(EpCourseKindExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EpCourseKind record);

    int insertSelective(EpCourseKind record);

    List<EpCourseKind> selectByExample(EpCourseKindExample example);

    EpCourseKind selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EpCourseKind record, @Param("example") EpCourseKindExample example);

    int updateByExample(@Param("record") EpCourseKind record, @Param("example") EpCourseKindExample example);

    int updateByPrimaryKeySelective(EpCourseKind record);

    int updateByPrimaryKey(EpCourseKind record);
}