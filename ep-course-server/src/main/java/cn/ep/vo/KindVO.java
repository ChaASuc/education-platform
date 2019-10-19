package cn.ep.vo;

import cn.ep.bean.EpCourseKind;
import lombok.Data;

import java.util.List;

@Data
public class KindVO {
    EpCourseKind root;
    List<EpCourseKind> subKind;
}
