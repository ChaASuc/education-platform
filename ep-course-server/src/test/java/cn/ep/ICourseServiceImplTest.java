package cn.ep;


import cn.ep.bean.EpCourse;
import cn.ep.mapper.EpCourseMapper;
import cn.ep.service.ICourseService;
import cn.ep.service.impl.ICourseServiceImpl;
import cn.ep.utils.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ICourseServiceImplTest extends TestUtil {

    @Autowired
    ICourseService courseService;
    @Autowired
    EpCourseMapper courseMapper;
    @Test
    public void insert(){
        EpCourse course = new EpCourse();
        course.setCourseName("spring");
        course.setFree(0);
        course.setGoal("打败美帝主义");
        course.setKindId(2L);
        course.setStatus(1);
        course.setUserId(1L);
        System.out.println(courseService.insert(course));
    }

    @Test
    public void updateById(){
        EpCourse course = new EpCourse();
        course.setId(1L);
        course.setFree(0);
        System.out.println(courseService.updateById(course));
    }

    @Test
    public  void  ft(){
        System.out.println(courseMapper.selectByKey("ing",1));
    }
}
