package cn.ep.service;

import cn.ep.bean.Product;
import cn.ep.bean.ProductDto;
import com.github.pagehelper.PageInfo;

import java.io.IOException;

public interface ProductService {
    /**
     * 外键类型
     */
    Integer ForeignKey_CATEGORY = 1;

    /**
     * 新增产品
     * @param product
     * @return
     */
    void insert(Product product);

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
    ProductDto select(int id);

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
    PageInfo<ProductDto> selectPageByCategory(int pageNum, int categoryId);

    /**
     * 根据外键信息和产品信息更新信息
     * @param id
     * @param product
     * @param foreignKey
     */
    void updateListByProductAndForeignKey(Integer id, Product product, Integer foreignKey);
}
