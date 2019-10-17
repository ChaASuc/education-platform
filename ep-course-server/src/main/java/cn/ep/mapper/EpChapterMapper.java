package cn.ep.mapper;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpChapterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EpChapterMapper {
    int countByExample(EpChapterExample example);

    int deleteByExample(EpChapterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EpChapter record);

    int insertSelective(EpChapter record);

    List<EpChapter> selectByExample(EpChapterExample example);

    EpChapter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EpChapter record, @Param("example") EpChapterExample example);

    int updateByExample(@Param("record") EpChapter record, @Param("example") EpChapterExample example);

    int updateByPrimaryKeySelective(EpChapter record);

    int updateByPrimaryKey(EpChapter record);

    int multiplyInsertSelective(@Param("chapterList") List<EpChapter> chapterList);
}