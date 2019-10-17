package cn.ep.mapper;

import cn.ep.bean.EpCourseUser;
import cn.ep.bean.EpCourseUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EpCourseUserMapper {
    int countByExample(EpCourseUserExample example);

    int deleteByExample(EpCourseUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EpCourseUser record);

    int insertSelective(EpCourseUser record);

    List<EpCourseUser> selectByExample(EpCourseUserExample example);

    EpCourseUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EpCourseUser record, @Param("example") EpCourseUserExample example);

    int updateByExample(@Param("record") EpCourseUser record, @Param("example") EpCourseUserExample example);

    int updateByPrimaryKeySelective(EpCourseUser record);

    int updateByPrimaryKey(EpCourseUser record);
}