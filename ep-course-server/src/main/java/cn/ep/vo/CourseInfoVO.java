package cn.ep.vo;

import cn.ep.bean.EpCourse;
import cn.ep.bean.EpUser;
import lombok.Data;

import java.util.List;

@Data
public class CourseInfoVO {
    private EpUser author;
    private EpCourse course;
    private float scope;
    private List<ChapterVO> chapters;
    private boolean login;
    private boolean subscription;
    private int nextChapter;
    private int nextVerse;
}
