package cn.ep.controller;


import cn.ep.bean.Product;
import cn.ep.bean.ProductDto;
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
import java.io.IOException;

@Api(description = "示例模块")
@RestController
@RequestMapping("json/example")
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
    @ApiOperation(value="新增产品", notes = "")
    @ApiImplicitParam(name= "product",value = "产品实体类", required = true, dataType = "Product")
    @PostMapping("product")
    @ResponseBody
    public ResultVO create(@RequestBody(required = false) Product product){
        productService.insert(product);
        // 删除缓存
        return ResultVO.success();
    }

//    /**
//     * 删除产品
//     * @param id
//     * @return
//     */
//    @ApiOperation(value = "根据产品id删除产品", notes = "")
//    @ApiImplicitParam(name = "id", value = "产品id", required = true, dataType = "Integer", paramType = "path")
//    @DeleteMapping("/product/{id}")
//    @ResponseBody
//    public Result destroy(@PathVariable Integer id){
//        boolean success = productService.delete(id);
//        if(success){
//            cache.delete(CacheNameHelper.Receive_CacheNamePrefix + id);
//            cache.deleteByPaterm(CacheNameHelper.List_CacheNamePrefix + "*");
//            cache.deleteByPaterm(CacheNameHelper.ListByCid_CacheNamePrefix + "*");
//            return Result.build(ResultType.Success);
//        }else{
//            return Result.build(ResultType.Failed);
//        }
//    }
//
//    /**
//     * 修改产品
//     * @param product
//     * @return
//     */
//    @ApiOperation(value="根据产品id修改产品信息")
//    @ApiImplicitParam(name="product", value = "产品实体类", required = true, dataType = "Product")
//    @PutMapping("product")
//    @ResponseBody
//    public Result update(@RequestBody(required = false) Product product){
//        if(product != null && product.getId() != null){
//            boolean success = productService.update(product);
//            if(success){
//                cache.delete(CacheNameHelper.Receive_CacheNamePrefix + product.getId());
//                cache.deleteByPaterm(CacheNameHelper.List_CacheNamePrefix + "*");
//                cache.deleteByPaterm(CacheNameHelper.ListByCid_CacheNamePrefix + "*");
//                return Result.build(ResultType.Success);
//            }else{
//                return Result.build(ResultType.Failed);
//            }
//        }
//        return Result.build(ResultType.ParamError);
//    }
//
//    /**
//     * 获取产品详情
//     * @param id
//     * @param response
//     * @throws IOException
//     */
//    @ApiOperation(value = "根据产品id获取产品详情", notes = "")
//    @ApiImplicitParam(name = "id", value = "产品id", required = true, dataType = "Integer", paramType = "path")
//    @GetMapping("product/{id}")
//    public void receive(@PathVariable Integer id,
//                        HttpServletResponse response) throws IOException {
//        response.setContentType("application/json;charset=utf-8");
//        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        String json = cache.get(cacheName);
//        if(json == null){
//            Product product = productService.select(id);
//            json = Result.build(ResultType.Success).appendData("product", product).convertIntoJSON();
//            if(product != null){
//                cache.set(cacheName, json);
//            }
//        }
//        response.getWriter().write(json);
//    }
//
//    /**
//     * 根据页码分页查询所有商品
//     * @param pageNum
//     * @param response
//     * @throws IOException
//     */
//    @ApiOperation(value = "根据页码分页查询所有商品", notes = "")
//    @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer", paramType = "path")
//    @GetMapping(value = "/products/list/{pageNum}")
//    public void list(
//            @PathVariable Integer pageNum ,
//                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json;charset=utf-8");
//        String cacheName = CacheNameHelper.List_CacheNamePrefix + pageNum;
//        String json = cache.get(cacheName);
//        if(json == null){
//            PageInfo<Product> pageInfo = productService.selectPage(pageNum);
//            json = Result.build(ResultType.Success).appendData("pageInfo", pageInfo).convertIntoJSON();
//            if(pageInfo != null) {
//                cache.set(cacheName, json);
//            }
//        }
//        response.getWriter().write(json);
//    }
//

    /**
     * 根据类别id和页码来分页查询商品
     *
     * @param cid
     * @param pageNum
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "根据类别id和页码来分页查询商品", notes = "测试中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "类别id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer", paramType = "path")
    })
    @GetMapping(value = "/products/listByCid/{cid}/{pageNum}")
    public ResultVO listByCid(@PathVariable Integer cid,
                          @PathVariable Integer pageNum,
                          HttpServletResponse response) throws IOException {
        // 获取缓存

        // 判断空

        PageInfo<ProductDto> productDtoPageInfo = productService.selectPageByCategory(pageNum, cid);

        // 添加缓存

        // 响应
        return ResultVO.success(productDtoPageInfo);
    }
}
