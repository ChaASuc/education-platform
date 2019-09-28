package cn.ep.controller;


import cn.ep.bean.Product;
import cn.ep.bean.ProductDto;
import cn.ep.enums.GlobalEnum;
import cn.ep.service.ProductService;
import cn.ep.utils.ResultVO;
import com.alibaba.druid.sql.visitor.functions.Substring;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.util.Integers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
//        scanner.nextInt()

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

    public static void main(String[] args){

//        test2();
//        test3();
        String str = "ab";
        System.out.println(str.substring(0,1));
    }

    private static void test3() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int a = scanner.nextInt();
            String s = scanner.nextLine();
            for (int i = 0; i <= s.length() - a; i++) {
                for (int j = i + a - 1; j <= s.length(); j++) {
                    s.substring(i, j);
                }
            }

        }

    }

    public static void test2() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int n = Integer.valueOf(s.charAt(0)) - 48;
        int m = Integer.valueOf(s.charAt(2)) -  48;
        String[] str = new String[n];
        int[] sum = new int[n];
        int[] value = new int[m];
        int max = 0;
        int bigSum = 0;
        for (int i = 0; i < n; i++) {
            str[i] = sc.nextLine();
        }

        for (int i = 0; i < m; i++) {
            value[i] = sc.nextInt();
            bigSum += value[i];
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < m; k++) {
                    if (str[i].charAt(k) == str[j].charAt(k)) {
                        sum[j] += value[k];
                    }
                }
                bigSum += sum[j];
            }
            if (bigSum > max) {
                max = bigSum;
            }
        }

        System.out.println(max);
    }

//    public static void test() {
//        Scanner sc = new Scanner(System.in);
//        int k = sc.nextInt();
//        String str = sc.nextLine();
//        char[] chars = str.toCharArray();
//        int sum = 0;
//        for (char c:
//                chars) {
//            if (c == '1') {
//                sum++;
//            }
//        }
//
//        if (sum < k) {
//            System.out.println(0);
//        }else {
//            char
//        }
//    }
//    public static int add(int a, int b){
//        return a + b;
//    }
}
