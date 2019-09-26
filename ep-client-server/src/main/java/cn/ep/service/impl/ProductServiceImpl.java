package cn.ep.service.impl;//package cn.ep.service.impl;

import cn.ep.bean.Category;
import cn.ep.bean.Product;
import cn.ep.bean.ProductDto;
import cn.ep.bean.ProductExample;
import cn.ep.client.ProductClient;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.mapper.ProductMapper;
import cn.ep.utils.JsonUtils;
import cn.ep.utils.ResultVO;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.ep.config.ExampleConfig;
import cn.ep.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductClient productClient;   // 提供者模块,跟本模块无关

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

        // 添加商品,有需要关联别的模块数据库,调用其他模块的productClient
        Integer cid = product.getCid();
        Category category = new Category();
        category.setId(cid);
        category.setName("小米");
        productClient.create(category);
    }

    /**
     * 删除产品
     * @param id
     * @return
     */
    public void delete(int id){
        boolean success = productMapper.deleteByPrimaryKey(id) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品删除失败");
        }
    }

    /**
     * 修改商品
     * @param product
     * @return
     */
    public void update(Product product){
        boolean success = productMapper.updateByPrimaryKeySelective(product) > 0 ? true : false;
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品更新失败");
        }
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    public Product select(int id){
        return productMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询所有商品,并返回其他信息
     * @param pageNum
     * @return
     */
    public PageInfo<ProductDto> selectPage(int pageNum){
        PageHelper.startPage(pageNum, globalConfig.getPageSize());    // 开启分页查询，第一次切仅第一次查询时生效
        List<Product> products = productMapper.selectByExample(null);   //  无条件查找商品
        if(products != null){
            List<ProductDto> productDtos = products.stream().map(
                    product -> {
                        ProductDto productDto = new ProductDto();
                        BeanUtils.copyProperties(product, productDto);
                        try {
                            productDto.setCategoryName(getCategory(product.getCid()).getName());
                        } catch (IOException e) {
                            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "类型转换失败");
                        }
                        return productDto;
                    }

            ).collect(Collectors.toList());

            return new PageInfo<>(productDtos);
        }
        else
            return null;
    }

    /**
     * 分类分页查询所有商品，并返回其他信息
     * @param pageNum
     * @param categoryId
     * @return
     */
    public PageInfo<ProductDto> selectPageByCategory(int pageNum, int categoryId) throws IOException {
        PageHelper.startPage(pageNum, globalConfig.getPageSize());    // 开启分页查询，第一次切仅第一次查询时生效
        // 创建查询条件
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(categoryId);
        // 根据条件查询
        List<Product> products = productMapper.selectByExample(example);   //  无条件查找商品

        if(products != null) {
            Category category = getCategory(categoryId);
            String categoryName = category.getName();

            List<ProductDto> productDtos = products.stream().map(
                    product -> {
                        ProductDto productDto = new ProductDto();
                        BeanUtils.copyProperties(product, productDto);
                        productDto.setCategoryName(categoryName);
                        return productDto;
                    }
            ).collect(Collectors.toList());
            return new PageInfo<>(productDtos);
        }
        else
            return new PageInfo<>(null);
    }

    private Category getCategory(int categoryId) throws IOException {
        // 根据种类id获取种类，其他服务接口
        ResultVO resultVO = productClient.receive(categoryId);
        Category category = JsonUtils.obj2Class(resultVO.getData(), Category.class);
        if (null == category) {
            throw new GlobalException(GlobalEnum.EXIST_ERROR, "该种类不存在");
        }
        return category;
    }
}
