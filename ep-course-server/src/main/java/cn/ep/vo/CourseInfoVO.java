package cn.ep.vo;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpCourse;
import cn.ep.bean.EpUser;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CourseInfoVO {
    private EpUser author;
    private EpCourse course;
    private float scope;
    private Map<EpChapter,List<ChapterVO>> chapters;
    private boolean login;
    private boolean subscription;
}
