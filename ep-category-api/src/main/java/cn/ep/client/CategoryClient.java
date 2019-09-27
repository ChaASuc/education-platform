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
        name = "ep-category",
        configuration = FeignConfig.class
)
public interface CategoryClient {

    /**
     * 新增类别
     * @param category
     * @return
     */
    @PostMapping("")
    ResultVO insert(@RequestBody Category category);

    /**
     * 根据主键id更新和逻辑删除种类
     * @param category
     * @return
     */
    @PutMapping("")
    ResultVO update(@RequestBody Category category);

//
//
    /**
     * 获取类别详情
     * @param id
     * @throws IOException
     */
    @GetMapping("/{id}")
    ResultVO getById(@PathVariable Integer id);

    /**
     * 列举所有类别
     */
    @GetMapping(value = "/listAll")
    ResultVO getListAll();
}
