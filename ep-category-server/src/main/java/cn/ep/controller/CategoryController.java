package cn.ep.controller;

import cn.ep.bean.Category;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.service.CategoryService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javafx.scene.CacheHint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(description = "示例模块")
@RestController
@RequestMapping("/ep/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisUtil redisUtil;
//
    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{

        // ep_category_prefix_getById_{类别id}
        public static final String EP_CATEGORY_PREFIX_GETBYID = "ep_category_prefix_getById_%s";

        // ep_category_prefix_getListAll
        public static final String EP_CATEGORY_PREFIX_GETLISTALL = "ep_category_prefix_getListAll";

        //ep_category_prefix_*  用于全部删除，避免缓存
        public static final String EP_CATEGORY_PREFIX = "ep_category_prefix_*";
    }

    /**
     * 新增类别
     * @param category
     * @return
     */
    @ApiOperation(value="新增类别", notes="已测试")
    @ApiImplicitParam(name = "category", value = "类别详情实体", required = true, dataType = "Category")
    @PostMapping("")
    public ResultVO insert(@RequestBody Category category){

        categoryService.insert(category);
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_CATEGORY_PREFIX);

        return ResultVO.success();
    }

    /**
     * 根据主键id更新和逻辑删除种类
     * @param category
     * @return
     */
    @ApiOperation(value="更新类别id更新类别信息或逻辑删除类", notes="已测试")
    @ApiImplicitParam(name = "category", value = "类别实体", required = true, dataType = "Category")
    @PutMapping("")
    public ResultVO update(@RequestBody Category category) {
        categoryService.update(category);
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_CATEGORY_PREFIX);

        return ResultVO.success();
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
    @GetMapping("/{id}")
    public ResultVO getById(@PathVariable Integer id){
        // 获取reids的key
        String key = String.format(CacheNameHelper.EP_CATEGORY_PREFIX_GETBYID, id);
        // 统一返回值
        Category category = new Category();
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            // 没有缓存，添加缓存
            category = categoryService.select(id);
            boolean success = redisUtil.set(key, category);
            if (!success) {
                throw new GlobalException(GlobalEnum.OPERATION_ERROR, "种类缓存失败");
            }
        }else {
            // 有缓存
            category = (Category) obj;
        }
        return ResultVO.success(category);
    }

    /**we
     * 列举所有类别
     */
    @ApiOperation(value="列举所有类别", notes="已测试")
    @GetMapping(value = "/listAll")
    public ResultVO getListAll(){
        // 获取reids的key
        String key = CacheNameHelper.EP_CATEGORY_PREFIX_GETLISTALL;
        // 统一返回值
        List<Category> categories = new ArrayList<>();
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            // 没有缓存，添加缓存
            categories = categoryService.selectAll();
            boolean success = redisUtil.set(key, categories);
            if (!success) {
                throw new GlobalException(GlobalEnum.OPERATION_ERROR, "种类缓存失败");
            }
        }else {
            // 有缓存
            categories = (List<Category>) obj;
        }
        return ResultVO.success(categories);
    }
}
