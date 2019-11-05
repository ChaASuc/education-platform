package cn.ep.mapper;

import cn.ep.bean.EpUserDetails;
import cn.ep.utils.JsonUtil;
import cn.ep.utils.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class EpUserMapperTest extends TestUtil {

    @Autowired
    private EpUserMapper epUserMapper;

    @Test
    public void Test() throws JsonProcessingException {
        List<EpUserDetails> epUserDetails = epUserMapper.selectUserDetailsNotPwd();
        System.out.println(JsonUtil.obj2String(epUserDetails));
    }
}