package cn.ep.controller;

import cn.ep.bean.EpPermission;
import cn.ep.bean.EpPermissionDto;
import cn.ep.service.EpPermissionService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/7
 * @Description
 * @Since 1.0.0
 */
@Api(description = "权限模块")
@RestController
@RequestMapping("/ep/user/perm")
@Validated
public class EpPermissionController {

    @Autowired
    private EpPermissionService epPermissionService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{

        //EP_PERMISSION_PREFIX_* 用于全部删除，避免缓存
        public static final String EP_PERMISSION_PREFIX = "ep_permission_prefix_*";

        public static final String EP_PERMISSION_PREFIX_GETBYDEPTIDANDNUM =
                "ep_permission_prefix_getByDeptId_%s_%s";
        public static final String EP_PERMISSION_PREFIX_GETLISTBYROLEIDANDNUM =
                "ep_permission_prefix_getListByRoleIdAndNum_%s_%s";
        public static final String EP_PERMISSION_PREFIX_GETLIST =
                "ep_permission_prefix_getList";
    }

//    /**
//     * 新增权限
//     * @param permission
//     * @return
//     */
//    @ApiOperation(value="新增权限", notes="已测试")
//    @ApiImplicitParam(name = "permission", value = "权限实体", required = true, dataType = "EpPermission")
//    @PostMapping("")
//    public ResultVO insert(@RequestBody @NotNull @Validated({Insert.class}) EpPermission permission){
//        epPermissionService.insert(permission);
//        // 清空相关缓存
//        redisUtil.delFuz(CacheNameHelper.EP_PERMISSION_PREFIX);
//        return ResultVO.success();
//    }

//    /**
//     * 根据主键修改和逻辑删除权限
//     * @param permission
//     * @return
//     */
//    @ApiOperation(value="根据主键修改和逻辑删除权限",notes = "已测试")
//    @ApiImplicitParam(name="permission", value = "权限实体类", dataType = "EpPermission")
//    @PutMapping("")
//    public ResultVO update(@RequestBody @NotNull @Validated({Update.class}) EpPermission permission){
//        epPermissionService.update(permission);
//        // 删除缓存
//        redisUtil.delFuz(CacheNameHelper.EP_PERMISSION_PREFIX);
//        return ResultVO.success();
//
//    }


    /**
     * 修改权限id和角色id集合修改
     * @param permissionId
     * @param roleId
     * @param type
     * @return
     */
    @ApiOperation(value="修改权限id和角色id集合修改,type=0 删除 1添加",notes = "已测试")
    @PutMapping("/{permissionId}/{roleId}/{type}")
    public ResultVO updateByPermissionIdAndRoleIdAndType(
            @PathVariable @Min(0) Long permissionId,
            @PathVariable @Min(0) Long roleId,
            @PathVariable @Min(0) @Max(1) Integer type
    ){
        boolean success = false;
        if (type == 0) {
            success = false;
        }else {
            success = success;
        }
        epPermissionService.updateRolePerm(permissionId, roleId, success);
        // 删除缓存
        redisUtil.delFuz(CacheNameHelper.EP_PERMISSION_PREFIX);
        return ResultVO.success();

    }



//    @ApiOperation(value="根据部门id获取权限信息", notes = "已测试")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name= "deptId",value = "部门id", required = true, paramType = "path"),
//            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
//    })
//    @GetMapping(value = "/dept/{deptId}/{num}")
//    public ResultVO getPermByDeptIdAndNum(
//            @PathVariable @NotNull @Min(0) Long deptId,
//            @PathVariable @NotNull @Min(1)Integer num) {
//        // 获取reids的key
//        String key = String.format(
//                CacheNameHelper.EP_PERMISSION_PREFIX_GETBYDEPTIDANDNUM, deptId, num);
//        // 统一返回值
//        PageInfo<EpPermission> permissions = null;
//        // 查看是否有缓存
//        Object obj = redisUtil.get(key);
//        if (null == obj) {
//            permissions = epPermissionService.selectByDeptIdAndNum(deptId, num);
//            redisUtil.set(key, permissions);
//        }else {
//            permissions = (PageInfo<EpPermission>) obj;
//        }
//        // 删除缓存
//        return ResultVO.success(permissions);
//    }


//    /**
//     * 获取用户的所有权限
//     * @return
//     */
//    @ApiOperation(value="获取角色的所有权限", notes="已测试")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name= "roleId",value = "角色id", required = true, paramType = "path"),
//            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
//    })
//    @GetMapping("/list/{roleId}/{num}")
//    public ResultVO getListByRoleIdAndNum(
//            @PathVariable @NotNull @Min(0) Long roleId,
//            @PathVariable @NotNull @Min(1) Integer num){
//        // 获取reids的key
//        String key = String.format(
//                CacheNameHelper.EP_PERMISSION_PREFIX_GETLISTBYROLEIDANDNUM, roleId, num);
//        // 统一返回值
//        PageInfo<EpPermission> permissions = null;
//        // 查看是否有缓存
//        Object obj = redisUtil.get(key);
//        if (null == obj) {
//            permissions = epPermissionService.selectByRoleIdAndNum(roleId, num);
//            redisUtil.set(key, permissions);
//        }else {
//            permissions = (PageInfo<EpPermission>) obj;
//        }
//        // 删除缓存
//        return ResultVO.success(permissions);
//    }


    /**
     * 获取用户的所有权限
     * @return
     */
    @ApiOperation(value="获取所有权限,及其对应角色", notes="已测试")
    @GetMapping("/list")
    public ResultVO getList(){
        // 获取reids的key
        String key =
                CacheNameHelper.EP_PERMISSION_PREFIX_GETLIST;
        // 统一返回值
        List<EpPermissionDto> permissionDtos = null;
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            permissionDtos = epPermissionService.selectEpPermissonDto();
            redisUtil.set(key, permissionDtos);
        }else {
            permissionDtos = (List<EpPermissionDto>) obj;
        }
        // 删除缓存
        return ResultVO.success(permissionDtos);
    }

    /**
     * 删除所有缓存
     * @return
     */
    @ApiOperation(value="删除所有缓存", notes="已测试")
    @GetMapping("/deleteAll")
    public ResultVO deleteAll(){
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_PERMISSION_PREFIX);
        return ResultVO.success();
    }




}
