package cn.ep.controller;


import cn.ep.bean.Product;
import cn.ep.bean.ProductDto;
import cn.ep.enums.GlobalEnum;
import cn.ep.service.ProductService;
import cn.ep.utils.ResultVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Api(description = "示例模块")
@RestController
@RequestMapping("/ep/product")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @Autowired
//    private RedisCache cache;
//
//    /**
//     * 内部类，专门用来管理每个方法所对应缓存的名称。
//     */
//    static class CacheNameHelper{
//        // e_p_product_{商品id}
//        private static final String Receive_CacheNamePrefix = "ep_product_";
//        // e_p_products_list_{页码}
//        private static final String List_CacheNamePrefix = "ep_products_list_";
//        // e_p_products_listByCid_{类别id}_{页码}
//        private static final String ListByCid_CacheNamePrefix = "ep_products_listByCid_";
//    }

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
        // 删除缓存
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
            // 删除缓存
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
        // 删除缓存
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
        // 获取缓存

        // 判断空

        ProductDto productDto = productService.select(id);

        // 添加缓存

        // 响应
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
        // 获取缓存

        // 判断空

        PageInfo<ProductDto> productDtoPageInfo = productService.selectPage(pageNum);

        // 添加缓存

        // 响应
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
        // 获取缓存

        // 判断空

        PageInfo<ProductDto> productDtoPageInfo = productService.selectPageByCategory(pageNum, cid);

        // 添加缓存

        // 响应
        return ResultVO.success(productDtoPageInfo);
    }
}
