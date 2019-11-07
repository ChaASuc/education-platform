package cn.ep.service.impl;

import cn.ep.bean.EpDeptDto;
import cn.ep.service.EpDeptService;
import cn.ep.utils.JsonUtil;
import cn.ep.utils.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.TestAnnotationUtils;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class EpDeptServiceImplTest extends TestUtil {

    @Autowired
    private EpDeptService epDeptService;

    @Test
    public void selectAll() throws JsonProcessingException {
        List<EpDeptDto> epDeptDtos = epDeptService.selectAll();
        log.info("【部门模块】epDeptDtos = {}", JsonUtil.obj2String(epDeptDtos));

    }
}