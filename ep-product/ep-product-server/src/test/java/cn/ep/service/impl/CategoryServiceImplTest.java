package cn.ep.service.impl;

import cn.ep.TestUtil;
import cn.ep.bean.Category;
import cn.ep.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class CategoryServiceImplTest extends TestUtil {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void insert() {
        Category category = new Category();
        category.setName("魅族");
        categoryService.insert(category);
    }

    @Test
    public void delete() {
        Category category = new Category();
        category.setId(6);
        category.setDeleted(true);
        categoryService.update(category);

    }

    @Test
    public void update() {
        Category category = new Category();
        category.setId(2);
        category.setName("小辣椒");
        categoryService.update(category);
    }

    @Test
    public void select() {
        Category category = categoryService.select(4);
        log.info("【种类模块】获取{}的种类 category = {}", 4, category);
    }

    @Test
    public void selectAll() {
        List<Category> categories =
                categoryService.selectAll();
        log.info("【种类模块】获取全部种类 categories = {}", categories);
    }
}