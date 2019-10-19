package cn.ep.vo;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpWatchRecord;
import lombok.Data;

import java.util.List;

@Data
public class ChapterVO {
    private EpChapter chapter;
    private List<VerseVO> verseVOS;
}
