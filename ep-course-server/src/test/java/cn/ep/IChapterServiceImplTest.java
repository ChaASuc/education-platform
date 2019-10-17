package cn.ep;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpCourseKind;
import cn.ep.mapper.EpChapterMapper;
import cn.ep.service.IChapterService;
import cn.ep.service.IKindService;
import cn.ep.utils.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

public class IChapterServiceImplTest extends TestUtil {
    @Autowired
    private IChapterService iChapterService;
    @Autowired
    private EpChapterMapper chapterMapper;

    @Test
    public void insert(){
        EpChapter epChapter = new EpChapter();
        epChapter.setChapterName("第四章断路器");
        epChapter.setCourseId(1L);
        System.out.println(iChapterService.insert(epChapter));
    }
    @Test
    public void updateById(){
        EpChapter epChapter = new EpChapter();
        epChapter.setId(1182840254013509632L);
        epChapter.setStatus(1);
        System.out.println(iChapterService.updateById(epChapter));
    }

    @Test
    public void getListByCourseIdAndStatus(){
        System.out.println(iChapterService.getListByCourseIdAndStatus(1,1));
    }

    @Test
    public void multiplyInsertSelective(){
        EpChapter chapter = new EpChapter();
        chapter.setId(55L);
        chapter.setCourseId(123456789L);
        chapter.setStatus(10);
        chapter.setDuration("测试批量插入");
        chapter.setUrl("123456789");
        EpChapter chapter1 = new EpChapter();
        chapter1.setId(65L);
        chapter1.setCourseId(123456789L);
        chapter1.setStatus(10);
        chapter1.setDuration("测试批量插入");
        chapter1.setUrl("123456789");
        List<EpChapter> chapterList = new ArrayList<>();
        chapterList.add(chapter);
        chapterList.add(chapter1);
        System.out.println(chapterList);
        System.out.println(chapterMapper.multiplyInsertSelective(chapterList));

    }
}
