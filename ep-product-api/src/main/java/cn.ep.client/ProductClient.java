package cn.ep.client;

import cn.ep.bean.Product;
import cn.ep.bean.ProductDto;
import cn.ep.enums.GlobalEnum;
import cn.ep.utils.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author deschen
 * @Create 2019/9/15
 * @Description
 * @Since 1.0.0
 */
@FeignClient(
        name = "ep-product",
        configuration = FeignConfig.class
)
public interface ProductClient {

    /**
     * 新增产品
     * @param product
     * @return
     */
    @PostMapping("/ep/product")
    ResultVO insert(@RequestBody(required = false) Product product);

    /**
     * 根据主键修改和逻辑删除产品
     * @param product
     * @return
     */
    @PutMapping("/ep/product")
    ResultVO update(@RequestBody Product product);


    /**
     * 根据外键逻辑删除产品
     * @param cid
     * @return
     */
    @PutMapping("/ep/product/list/{cid}")
    ResultVO updateListByCid(@PathVariable Integer cid);

//
    /**
     * 获取产品详情
     * @param id
     */
    @GetMapping("/ep/product/{id}")
    ResultVO getById(@PathVariable Integer id);
//
    /**
     * 根据页码分页查询所有商品
     * @param pageNum
     */
    @GetMapping(value = "/ep/product/list/{pageNum}")
    ResultVO getListByPageNum(@PathVariable Integer pageNum);
//

    /**
     * 根据类别id和页码来分页查询商品
     *
     * @param cid
     * @param pageNum
     */
    @GetMapping(value = "/ep/product/list/{cid}/{pageNum}")
    ResultVO getListByCidAndPageNum(@PathVariable Integer cid, @PathVariable Integer pageNum);

}
