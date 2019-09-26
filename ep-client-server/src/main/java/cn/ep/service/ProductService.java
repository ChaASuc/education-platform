package cn.ep.service;

import cn.ep.bean.Product;
import cn.ep.bean.ProductDto;
import com.github.pagehelper.PageInfo;

import java.io.IOException;

public interface ProductService {
    /**
     * 新增产品
     * @param product
     * @return
     */
    void insert(Product product);

    /**
     * 删除产品
     * @param id
     * @return
     */
    void delete(int id);

    /**
     * 修改和删除商品
     * @param product
     * @return
     */
    void update(Product product);

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    Product select(int id);

    /**
     * 分页查询所有商品
     * @param pageNum
     * @return
     */
    PageInfo<ProductDto> selectPage(int pageNum);

    /**
     * 分类分页查询商品
     * @param pageNum
     * @param categoryId
     * @return
     */
    PageInfo<ProductDto> selectPageByCategory(int pageNum, int categoryId) throws IOException;
}
