package cn.ep.mapper;

import cn.ep.bean.EpPermission;
import cn.ep.properties.EpUserProperties;
import cn.ep.utils.JsonUtil;
import cn.ep.utils.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class EpPermissionMapperTest extends TestUtil {

    @Autowired
    private EpPermissionMapper epPermissionMapper;


    @Autowired
    private EpUserProperties epUserProperties;

    @Test
    public void selectByRoleId() throws JsonProcessingException {
//        List<EpPermission> epPermissions = epPermissionMapper.selectByRoleId(1181241956957818880L);
//        System.out.println(JsonUtil.obj2String(epPermissions));
        System.out.println(epUserProperties.getRole().getUserId());
    }
}