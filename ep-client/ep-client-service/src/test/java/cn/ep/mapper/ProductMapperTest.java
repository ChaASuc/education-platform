package cn.ep.mapper;

import cn.ep.bean.Product;
import cn.ep.utils.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ProductMapperTest extends TestUtil {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void test() {
        List<Product> products =
                productMapper.selectByExample(null);

    }
}