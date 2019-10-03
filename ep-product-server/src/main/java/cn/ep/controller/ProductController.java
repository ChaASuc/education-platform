package cn.ep.controller;


import cn.ep.bean.Product;
import cn.ep.bean.ProductDto;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.service.ProductService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
//import cn.ep.utils.StringRedisCache;
import com.alibaba.druid.sql.visitor.functions.Substring;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.util.Integers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

@Api(description = "示例模块")
@RestController
@RequestMapping("/ep/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisUtil redisUtil;
//
    /**
     * 内部类，专门用来管理每个方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        // ep_product_prefix_getById_{商品id}
        private static final String EP_PRODUCT_PREFIX_GETBYID = "ep_product_prefix_getById_%s";
        // ep_product_prefix_getListByPageNum_{页码}
        private static final String EP_PRODUCT_PREFIX_GETLISTBYPAGENUM = "ep_product_prefix_getListByPageNum_%s";
        // ep_product_prefix_getlistByCidAndPageNum_{类别id}_{页码}
        private static final String EP_PRODUCT_PREFIX_GETLISTBYCIDANDPAGENUM = "ep_product_prefix_getlistByCidAndPageNum_%s_%s";
        // ep_product_prefix_*
        private static final String EP_PRODUCT_PREFIX = "ep_product_prefix_*";
    }

    /**
     * 新增产品
     * @param product
     * @return
     */
    @ApiOperation(value="新增产品", notes = "已测试")
    @ApiImplicitParam(name= "product",value = "产品实体类", required = true, dataType = "Product")
    @PostMapping("")
    public ResultVO insert(@RequestBody(required = false) Product product){
        productService.insert(product);
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_PRODUCT_PREFIX);
        return ResultVO.success();
    }

    /**
     * 根据主键修改和逻辑删除产品
     * @param product
     * @return
     */
    @ApiOperation(value="根据产品id修改跟新产品信息",notes = "已测试")
    @ApiImplicitParam(name="product", value = "产品实体类", required = true, dataType = "Product")
    @PutMapping("")
    public ResultVO update(@RequestBody Product product){
        if(product != null && product.getId() != null){
            productService.update(product);
            // 清空相关缓存
            redisUtil.delFuz(CacheNameHelper.EP_PRODUCT_PREFIX);
            return ResultVO.success();
        }
        return ResultVO.failure(GlobalEnum.PARAMS_ERROR);
    }


    /**
     * 根据外键逻辑删除产品
     * @param cid
     * @return
     */
    @ApiOperation(value="根据种类id逻辑删除产品信息",notes = "已测试")
    @ApiImplicitParam(name="cid", value = "种类id", required = true, dataType = "Integer", paramType = "path")
    @DeleteMapping("/list/{cid}")
    public ResultVO deleteListByCid(@PathVariable Integer cid){
        Product product = new Product();
        product.setDeleted(true);
        productService.updateListByProductAndForeignKey(cid, product, ProductService.ForeignKey_CATEGORY);
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_PRODUCT_PREFIX);
        return ResultVO.success();
    }

//
    /**
     * 获取产品详情
     * @param id
     * @throws IOException
     */
    @ApiOperation(value = "根据产品id获取产品详情", notes = "已测试")
    @ApiImplicitParam(name = "id", value = "产品id", required = true, dataType = "Integer", paramType = "path")
    @GetMapping("/{id}")
    public ResultVO getById(@PathVariable Integer id) {
        // 获取reids的key
        String key = String.format(CacheNameHelper.EP_PRODUCT_PREFIX_GETBYID, id);
        // 统一返回值
        ProductDto productDto = null;
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            // 没有缓存，添加缓存
            productDto = productService.select(id);
            boolean success = redisUtil.set(key, productDto);
            if (!success) {
                throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品缓存失败");
            }
        }else {
            // 有缓存
            productDto = (ProductDto) obj;
        }

        return ResultVO.success(productDto);
    }
//
    /**
     * 根据页码分页查询所有商品
     * @param pageNum
     * @throws IOException
     */
    @ApiOperation(value = "根据页码分页查询所有商品", notes = "已测试")
    @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer", paramType = "path")
    @GetMapping(value = "/list/{pageNum}")
    public ResultVO getListByPageNum(@PathVariable Integer pageNum){
        // 获取reids的key
        String key = String.format(CacheNameHelper.EP_PRODUCT_PREFIX_GETLISTBYPAGENUM, pageNum);
        // 统一返回值
        PageInfo<ProductDto> productDtoPageInfo = null;
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            // 没有缓存，添加缓存
            productDtoPageInfo = productService.selectPage(pageNum);
            boolean success = redisUtil.set(key, productDtoPageInfo);
            if (!success) {
                throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品缓存失败");
            }
        }else {
            // 有缓存
            productDtoPageInfo = (PageInfo<ProductDto>) obj;
        }

        return ResultVO.success(productDtoPageInfo);
    }
//

    /**
     * 根据类别id和页码来分页查询商品
     *
     * @param cid
     * @param pageNum
     * @throws IOException
     */
    @ApiOperation(value = "根据类别id和页码来分页查询商品", notes = "已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "类别id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer", paramType = "path")
    })
    @GetMapping(value = "/list/{cid}/{pageNum}")
    public ResultVO getListByCidAndPageNum(@PathVariable Integer cid, @PathVariable Integer pageNum) {
        // 获取reids的key
        String key = String.format(CacheNameHelper.EP_PRODUCT_PREFIX_GETLISTBYCIDANDPAGENUM, cid, pageNum);
        // 统一返回值
        PageInfo<ProductDto> productDtoPageInfo = null;
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            // 没有缓存，添加缓存
            productDtoPageInfo = productService.selectPageByCategory(pageNum, cid);
            boolean success = redisUtil.set(key, productDtoPageInfo);
            if (!success) {
                throw new GlobalException(GlobalEnum.OPERATION_ERROR, "商品缓存失败");
            }
        }else {
            // 有缓存
            productDtoPageInfo = (PageInfo<ProductDto>) obj;
        }

        return ResultVO.success(productDtoPageInfo);
    }

}
