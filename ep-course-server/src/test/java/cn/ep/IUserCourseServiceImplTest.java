package cn.ep;

import cn.ep.bean.EpChapter;
import cn.ep.mapper.EpChapterMapper;
import cn.ep.service.IChapterService;
import cn.ep.service.ICourseUserService;
import cn.ep.utils.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class IUserCourseServiceImplTest extends TestUtil {
    @Autowired private ICourseUserService courseUserService;

    @Test
    public void getListByUserId(){
        System.out.println(courseUserService.getListByUserId(1L));
    }
}
