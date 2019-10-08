package cn.ep.mapper;

import cn.ep.bean.EpUser;
import cn.ep.utils.TestUtil;
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


}