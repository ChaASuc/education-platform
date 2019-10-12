package cn.ep;

import cn.ep.bean.EpChapter;
import cn.ep.bean.EpCourseKind;
import cn.ep.service.IChapterService;
import cn.ep.service.IKindService;
import cn.ep.utils.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

public class IChapterServiceImplTest extends TestUtil {
    @Autowired
    private IChapterService iChapterService;
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

}
