package cn.ep.controller;

import cn.ep.bean.Category;
import cn.ep.enums.GlobalEnum;
import cn.ep.service.CategoryService;
import cn.ep.utils.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(description = "示例模块")
@RestController
@RequestMapping("json/example")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    @Autowired
//    private RedisCache cache;
//
//    /**
//     * 内部类，专门用来管理每个get方法所对应缓存的名称。
//     */
//    static class CacheNameHelper{
//        // e_c_category_{类别id}
//        public static final String Receive_CacheNamePrefix = "e_category_";
//        // e_c_categories_listAll
//        public static final String ListAll_CacheName = "e_categories_listAll";
//    }

    /**
     * 新增类别
     * @param category
     * @return
     */
    @ApiOperation(value="新增类别", notes="已测试")
    @ApiImplicitParam(name = "category", value = "类别详情实体", required = true, dataType = "Category")
    @PostMapping("/category")
    @ResponseBody
    public ResultVO create(@RequestBody(required = false) Category category){

        categoryService.insert(category);

        // 清空相关缓存 todo

        return ResultVO.success();
    }

    /**
     * 更新类别
     * @param category
     * @return
     */
    @ApiOperation(value="更新类别id更新类别信息或逻辑删除类", notes="已测试")
    @ApiImplicitParam(name = "category", value = "类别实体", required = true, dataType = "Category")
    @PutMapping("/category")
    public ResultVO update(@RequestBody(required = false) Category category) {
        if (category != null && category.getId() != null) {
            // 若是逻辑删除,就要更改其deleted字段
            categoryService.update(category);
            // 清空缓存
            return ResultVO.success();
        }
        return ResultVO.failure(GlobalEnum.PARAMS_ERROR, "种类不为空或无id");
    }
//
//
    /**
     * 获取类别详情
     * @param id
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取类别详情", notes="已测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Integer", paramType = "path")
    @GetMapping("/category/{id}")
    public ResultVO receive(@PathVariable Integer id){
        // 查看是否有缓存
        // 没有缓存，添加缓存
        Category category = categoryService.select(id);
        return ResultVO.success(category);
    }

    /**
     * 列举所有类别
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="列举所有类别", notes="已测试")
    @GetMapping(value = "/categories/listAll")
    public ResultVO list(HttpServletResponse response){
        // 查看是否有缓存
        // 没有缓存，添加缓存
        List<Category> categories = categoryService.selectAll();
        return ResultVO.success(categories);
    }
}
