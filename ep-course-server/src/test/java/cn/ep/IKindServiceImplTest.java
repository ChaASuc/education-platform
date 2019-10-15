package cn.ep;

import cn.ep.bean.EpCourseKind;
import cn.ep.service.IKindService;
import cn.ep.utils.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IKindServiceImplTest extends TestUtil {

    @Autowired
    private IKindService iKindService;

    @Test
    public void getListByStatus(){
        System.out.println(iKindService.getListByStatus(1));
    }

    @Test
    public void getListByRootAndStatus(){
        System.out.println(iKindService.getListByRootAndStatus(1,1L));
    }

    @Test
    public void insert(){
        EpCourseKind kind = new EpCourseKind();
        kind.setKindName("html");
        kind.setRoot(1L);
        kind.setStatus(1);
        System.out.println(iKindService.insert(kind));
    }

    @Test
    public void updateById(){
        EpCourseKind kind = new EpCourseKind();
        kind.setStatus(1);
        kind.setId(1182626333419769856L);
        System.out.println(iKindService.updateById(kind));
    }

    @Test
    public void getSubKindList(){
        System.out.println(iKindService.getSubKindList());
    }
}
