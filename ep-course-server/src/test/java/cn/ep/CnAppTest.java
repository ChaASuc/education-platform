package cn.ep;

import cn.ep.service.IKindService;
import cn.ep.utils.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CnAppTest extends TestUtil {

    @Autowired
    private IKindService iKindService;
    @Test
    public void IKindServiceImplTest(){
        System.out.println(iKindService.getListByStatus(1));
    }
}
