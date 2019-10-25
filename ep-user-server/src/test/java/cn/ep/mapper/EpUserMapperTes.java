package cn.ep.mapper;

import cn.ep.bean.EpUser;
//import cn.ep.bean.EpUserDetails;
import cn.ep.bean.EpUserDetails;
import cn.ep.utils.JsonUtil;
import cn.ep.utils.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class EpUserMapperTes extends TestUtil {

    @Autowired
    private EpUserMapper epUserMapper;

    @Test
    public void test() {
        List<EpUser> epUsers = epUserMapper.selectByExample(null);
    }

    @Test
    public void selectByUsernameTest() throws JsonProcessingException {
        EpUserDetails deschen =
                epUserMapper.selectByUsername("deschen", 1);
        System.out.println(JsonUtil.obj2String(deschen));

        EpUserDetails phone =
                epUserMapper.selectByUsername("1234567890", 2);
        System.out.println(JsonUtil.obj2String(phone));
    }
}