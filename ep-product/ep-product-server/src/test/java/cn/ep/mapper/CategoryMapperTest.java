package cn.ep.mapper;

import cn.ep.bean.Category;
import cn.ep.bean.CategoryExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void test() {
        List<Category> categories =
                categoryMapper.selectByExample(new CategoryExample());
        System.out.println(categories);
    }
}