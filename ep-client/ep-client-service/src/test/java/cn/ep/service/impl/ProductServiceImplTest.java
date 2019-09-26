package cn.ep.service.impl;

import cn.ep.bean.Product;
import cn.ep.service.ProductService;
import cn.ep.utils.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class ProductServiceImplTest extends TestUtil {

    @Autowired
    private ProductService productService;

    @Test
    public void insert() {
        Product product = new Product();
        product.setName("小米");
        product.setId(4);
        product.setCid(1);
        productService.insert(product);
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void select() {
    }

    @Test
    public void selectPage() {
    }

    @Test
    public void selectPageByCategory() {
    }
}