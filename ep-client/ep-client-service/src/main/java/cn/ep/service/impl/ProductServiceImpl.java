//package cn.ep.service.impl;
//
//import cn.ep.bean.Category;
//import cn.ep.bean.Product;
//import cn.ep.bean.ProductExample;
//import cn.ep.client.ProductClient;
//import cn.ep.enums.GlobalEnum;
//import cn.ep.exception.GlobalException;
//import cn.ep.mapper.ProductMapper;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import cn.ep.config.ExampleConfig;
//import cn.ep.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//    @Autowired
//    private ProductClient productClient;   // 提供者模块,跟本模块无关
//
//    @Autowired
//    private ProductMapper productMapper;    // 爆红不管
//
//
//    @Autowired
//    private ExampleConfig globalConfig;
//
//    /**
//     * 新增产品
//     * @param product
//     * @return
//     */
//    @Transactional
//    public void insert(Product product){
//        boolean success = productMapper.insertSelective(product) > 0 ? true : false;
//        if (!success) {
//            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品添加失败");
//        }
//
//        // 添加商品,有需要关联别的模块数据库,调用其他模块的productClient
//        Integer cid = product.getCid();
//        Category category = new Category();
//        category.setId(cid);
//        category.setName("小米");
//        productClient.create(category);
//    }
//
//    /**
//     * 删除产品
//     * @param id
//     * @return
//     */
//    public void delete(int id){
//        boolean success = productMapper.deleteByPrimaryKey(id) > 0 ? true : false;
//        if (!success) {
//            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品删除失败");
//        }
//    }
//
//    /**
//     * 修改商品
//     * @param product
//     * @return
//     */
//    public void update(Product product){
//        boolean success = productMapper.updateByPrimaryKeySelective(product) > 0 ? true : false;
//        if (!success) {
//            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品更新失败");
//        }
//    }
//
//    /**
//     * 查询商品详情
//     * @param id
//     * @return
//     */
//    public Product select(int id){
//        return productMapper.selectByPrimaryKey(id);
//    }
//
//    /**
//     * 分页查询所有商品,并返回其他信息
//     * @param pageNum
//     * @return
//     */
//    public PageInfo<Product> selectPage(int pageNum){
//        PageHelper.startPage(pageNum, globalConfig.getPageSize());    // 开启分页查询，第一次切仅第一次查询时生效
//        List<Product> products = productMapper.selectByExample(null);   //  无条件查找商品
//        if(products != null)
//            return new PageInfo<>(products);
//        else
//            return null;
//    }
//
//    /**
//     * 分类分页查询所有商品，并返回其他信息
//     * @param pageNum
//     * @param categoryId
//     * @return
//     */
//    public PageInfo<Product> selectPageByCategory(int pageNum, int categoryId){
//        PageHelper.startPage(pageNum, globalConfig.getPageSize());    // 开启分页查询，第一次切仅第一次查询时生效
//        // 创建查询条件
//        ProductExample example = new ProductExample();
//        ProductExample.Criteria criteria = example.createCriteria();
//        criteria.andCidEqualTo(categoryId);
//        // 根据条件查询
//        List<Product> products = productMapper.selectByExample(example);   //  无条件查找商品
//        if(products != null)
//            return new PageInfo<>(products);
//        else
//            return null;
//    }
//}
