package cn.ep;

import cn.ep.service.ICheckService;
import cn.ep.utils.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ICheckServiceImplTest extends TestUtil {
    @Autowired
    private ICheckService checkService;

    @Test
    public void getById(){
        System.out.println(checkService.getById(55555L));
    }
}
