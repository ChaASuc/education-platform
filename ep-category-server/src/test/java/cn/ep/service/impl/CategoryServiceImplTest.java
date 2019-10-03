package cn.ep.service.impl;

import cn.ep.utils.RedisUtil;
import cn.ep.utils.TestUtil;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class CategoryServiceImplTest extends TestUtil {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void insert() {
        redisUtil.set("5", "ni号");

        System.out.println(redisUtil.get("5"));
    }

    @Test
    public void update() {
    }

    @Test
    public void select() {
    }

    @Test
    public void selectAll() {
    }
}