package cn.ep.mapper;

import cn.ep.bean.EpDept;
import cn.ep.utils.IdWorker;
import cn.ep.utils.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class EpDeptMapperTest extends TestUtil {


    @Autowired
    private EpDeptMapper epDeptMapper;

    @Autowired
    private IdWorker idWorker;

    @Test
    public void insertSelectiveTest() {
        EpDept epDept = new EpDept();
        epDept.setDeptId(idWorker.nextId());
        epDept.setDeptName("角色管理");
        int i = epDeptMapper.insertSelective(epDept);
        Assert.assertNotEquals(0, i);
    }

}