package cn.ep.vo;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpWatchRecord;
import lombok.Data;

@Data
public class VerseVO {
    private EpChapter verse;
    private EpWatchRecord record;
}
