package cn.ep.client;

import cn.ep.bean.Category;
import cn.ep.enums.GlobalEnum;
import cn.ep.utils.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
public interface CategoryClient {

    /**
     * 新增类别
     * @param category
     * @return
     */
    @PostMapping("/ep/category")
    ResultVO create(@RequestBody(required = false) Category category);

    /**
     * 更新和删除类别
     * @param category
     * @return
     */
    @PutMapping("/ep/category")
    ResultVO update(@RequestBody(required = false) Category category);

    /**
     * 获取类别详情
     * @param id
     * @throws IOException
     */
    @GetMapping("/ep/category/{id}")
    ResultVO receive(@PathVariable Integer id);

    /**
     * 列举所有类别
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/ep/categories/listAll")
    ResultVO list(HttpServletResponse response);
}
