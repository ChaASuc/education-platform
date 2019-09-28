package cn.ep.service.impl;//package cn.ep.service.impl;//package cn.ep.service.impl;
//
import cn.ep.bean.Category;
import cn.ep.bean.Product;
import cn.ep.bean.ProductDto;
import cn.ep.bean.ProductExample;
import cn.ep.client.CategoryClient;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.ProductMapper;
import cn.ep.utils.JsonUtil;
import cn.ep.utils.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.ep.config.ExampleConfig;
import cn.ep.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryClient categoryClient;   // 提供者模块,跟本模块无关

    @Autowired
    private ProductMapper productMapper;    // 爆红不管


    @Autowired
    private ExampleConfig globalConfig;

    /**
     * 新增产品
     * @param product
     * @return
     */
    @Transactional
    public void insert(Product product){
        boolean success = productMapper.insertSelective(product) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品添加失败");
        }

        // 添加商品,有需要关联别的模块数据库,调用其他模块的categoryClient
        Integer cid = product.getCid();
        Category category = new Category();
        category.setId(cid);
        category.setName("小米");
        categoryClient.insert(category);
    }

    /**
     * 修改和删除商品
     * @param product
     * @return
     */
    @Transactional
    public void update(Product product){
        boolean success = productMapper.updateByPrimaryKeySelective(product) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品更新失败");
        }
    }

//    /**
//     * 根据种类逻辑删除商品
//     * @param cids
//     * @return
//     */
//    public void updateByCid(List<Integer> cids){
//        ProductExample productExample = new ProductExample();
//        productExample.createCriteria()
//                .andCidIn(cids);
//        Product product = new Product();
//        product.setDeleted(false);
//        boolean success = productMapper.updateByExampleSelective(product, productExample) > 0 ? true : false;
//        if (!success) {
//            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品更新失败");
//        }
//    }
    /**
     * 查询商品详情
     * @param id
     * @return
     */
    public ProductDto select(int id){
        Product product = productMapper.selectByPrimaryKey(id);
        // 判断是否存在，逻辑删除判断
        if (product.getDeleted()) {
            return null;
        }

        Category category = getCategory(product.getCid());
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        productDto.setCategoryName(category.getName());
        return productDto;
    }

    /**
     * 分页查询所有商品,并返回其他信息
     * @param pageNum
     * @return
     */
    public PageInfo<ProductDto> selectPage(int pageNum){
        PageHelper.startPage(pageNum, globalConfig.getPageSize());    // 开启分页查询，第一次切仅第一次查询时生效
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andDeletedEqualTo(false);
        List<Product> products = productMapper.selectByExample(productExample);   //  无条件查找商品
        if(products != null){
            List<ProductDto> productDtos = products.stream().map(
                    product -> {
                        ProductDto productDto = new ProductDto();
                        BeanUtils.copyProperties(product, productDto);
                        productDto.setCategoryName(getCategory(product.getCid()).getName());
                        return productDto;
                    }

            ).collect(Collectors.toList());
            return new PageInfo<>(productDtos);
        }
        else
            return new PageInfo<>(null);
    }

    /**
     * 分类分页查询所有商品，并返回其他信息
     * @param pageNum
     * @param categoryId
     * @return
     */
    public PageInfo<ProductDto> selectPageByCategory(int pageNum, int categoryId){
        PageHelper.startPage(pageNum, globalConfig.getPageSize());    // 开启分页查询，第一次切仅第一次查询时生效
        // 创建查询条件
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(categoryId).andDeletedEqualTo(false);
        // 根据条件查询
        List<Product> products = productMapper.selectByExample(example);

        List<ProductDto> productDtos = new ArrayList<>();
        //  无商品
        if(!CollectionUtils.isEmpty(products)) {
            Category category= getCategory(categoryId);
            String categoryName = category.getName();

            productDtos = products.stream().map(
                    product -> {
                        ProductDto productDto = new ProductDto();
                        BeanUtils.copyProperties(product, productDto);
                        productDto.setCategoryName(categoryName);
                        return productDto;
                    }
            ).collect(Collectors.toList());
            return new PageInfo<>(productDtos);
        } else {
                return new PageInfo<>(productDtos);
        }
    }

    /**
     * 根据外键id和产品信息逻辑更新产品
     *
     * @param id
     */
    @Override
    @Transactional
    public void updateListByProductAndForeignKey(Integer id, Product product, Integer foreignKey) {
        ProductExample productExample = new ProductExample();
        // 判断该建属于那种外键
        if (foreignKey.equals(ProductService.ForeignKey_CATEGORY)) {
            productExample.createCriteria().andCidEqualTo(id);
        }//else if{..}

        productMapper.updateByExampleSelective(product, productExample);
        boolean success = productMapper.updateByExampleSelective(product, productExample) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "产品删除失败");
        }
    }

    private Category getCategory(int categoryId){
        // 根据种类id获取种类，调用其他服务接口
        ResultVO resultVO = categoryClient.getById(categoryId);
        // 判断是否正确返回
        if (!resultVO.getCode().equals(GlobalEnum.SUCCESS.getCode())) {
            throw new GlobalException(GlobalEnum.EXIST_ERROR, "种类");
        }

        Category category = null;
        try {
            category = JsonUtil.obj2Class(resultVO.getData(), Category.class);
        } catch (IOException e) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "类型转换失败");
        }
        if (null == category) {
            throw new GlobalException(GlobalEnum.EXIST_ERROR, "种类");
        }
        return category;
    }
}
