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
        course.setCourseName("打倒肖华莫雷");
        course.setFree(1);
        course.setGoal("测试数据");
        course.setKindId(4L);
        course.setStatus(1);
        course.setUserId(1L);
        for (int i = 0; i< 200; i++){
            System.out.println(courseService.insert(course));
        }
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
        System.out.println(courseService.insert(course));
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
    public void getListByTop(){
        System.out.println(courseService.getListByTop(1,0,20));
    }

    @Test
    public void getListByKindIdAndFreeAndOrder(){
        System.out.println(courseService.getListByKindIdAndFreeAndOrder(1184017944519249920L,1,3));
    }
    @Test
    public  void  ft(){
        System.out.println(courseMapper.selectByKey("ing",1));
    }
}
