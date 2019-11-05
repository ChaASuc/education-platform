package cn.ep.controller;

import cn.ep.bean.EpRole;
import cn.ep.bean.EpUserDetails;
import cn.ep.bean.EpUserRole;
import cn.ep.properties.EpUserProperties;
import cn.ep.service.EpRoleService;
import cn.ep.utils.Oauth2Util;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/7
 * @Description
 * @Since 1.0.0
 */
@Api(description = "角色模块")
@RestController
@RequestMapping("/ep/user/role")
@Validated
public class EpRoleController {

    @Autowired
    private EpRoleService epRoleService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private Oauth2Util oauth2Util;

    @Autowired
    private EpUserProperties epUserProperties;

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{

        // ep_category_prefix_getByUserNicknameAnduserPwd_{角色名}_{密码}
        public static final String EP_ROLE_PREFIX_GETBYROLENICKNAMEANDROLEPWD =
                "ep_ROLE_prefix_getByROLENicknameAndROLEPwd_%s_%s";

        //ep_ROLE_prefix_* 用于全部删除，避免缓存
        public static final String EP_ROLE_PREFIX = "ep_role_prefix_*";

        // ep_ROLE_prefix_getByROLENicknameAndType_{角色名}_{角色名类型}
        public static final String EP_ROLE_GETBYROLENICKNAMEANDTYPE =
                "ep_role_prefix_getByROLENicknameAndType_%s_%s";
        public static final String EP_ROLE_PREFIX_GETBYDEPTIDANDNUM =
                "ep_role_prefix_getByDeptId_%s_%s";
        public static final String EP_ROLE_PREFIX_GETLISTBYUSERIDANDNUM =
                "ep_role_prefix_getListByUserId_%s";

        public static final String EP_ROLE_PREFIX_GETLIST =
                "ep_role_prefix_getList";
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @ApiOperation(value="新增角色", notes="已测试")
    @ApiImplicitParam(name = "role", value = "角色实体", required = true, dataType = "EpRole")
    @PostMapping("")
    public ResultVO insert(@RequestBody @NotNull @Validated({Insert.class}) EpRole role){
        epRoleService.insert(role);
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_ROLE_PREFIX);
        return ResultVO.success();
    }


    /**
     * 提升角色
     * @param userId
     * @return
     */
    @ApiOperation(value="提升角色", notes="已测试")
//    @ApiImplicitParam(name = "role", value = "角色实体", required = true, dataType = "EpRole")
    @PostMapping("/teacher/{userId}")
    public ResultVO insertUserRole(@PathVariable @Min(0) Long userId){
        epRoleService.insertUserRole(userId, epUserProperties.getRole().getTeacherId());
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_ROLE_PREFIX);
        redisUtil.delFuz(EpUserController.CacheNameHelper.EP_USER_PREFIX);
        return ResultVO.success();
    }

    /**
     * 降级角色
     * @param userId
     * @return
     */
    @ApiOperation(value="降级角色", notes="已测试")
    @PutMapping("/student/{userId}/{roleId}")
    public ResultVO updateUserRole(@PathVariable @Min(0) Long userId,
                                   @PathVariable @Min(0) Long roleId) {
        epRoleService.updateUserRole(userId, roleId);
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_ROLE_PREFIX);
        redisUtil.delFuz(EpUserController.CacheNameHelper.EP_USER_PREFIX);
        return ResultVO.success();
    }

    /**
     * 根据主键修改和逻辑删除角色
     * @param role
     * @return
     */
    @ApiOperation(value="根据主键修改和逻辑删除角色",notes = "已测试")
    @ApiImplicitParam(name="role", value = "角色实体类", dataType = "EpRole")
    @PutMapping("")
    public ResultVO update(@RequestBody @NotNull @Validated({Update.class}) EpRole role){
        epRoleService.update(role);
        // 删除缓存
        redisUtil.delFuz(CacheNameHelper.EP_ROLE_PREFIX);
        return ResultVO.success();

    }



//    @ApiOperation(value="根据部门id获取角色信息", notes = "已测试")
//    @GetMapping(value = "/dept/{deptId}/{num}")
//    public ResultVO getRoleByDeptIdAndNum(
//            @PathVariable @NotNull @Min(0) Long deptId,
//            @PathVariable @NotNull @Min(1)Integer num) {
//        // 获取reids的key
//        String key = String.format(
//                CacheNameHelper.EP_ROLE_PREFIX_GETBYDEPTIDANDNUM, deptId, num);
//        // 统一返回值
//        PageInfo<EpRole> roles = null;
//        // 查看是否有缓存
//        Object obj = redisUtil.get(key);
//        if (null == obj) {
//            roles = epRoleService.selectByDeptIdAndNum(deptId, num);
//            redisUtil.set(key, roles);
//        }else {
//            roles = (PageInfo<EpRole>) obj;
//        }
//        // 删除缓存
//        return ResultVO.success(roles);
//    }

    @ApiOperation(value="根据部门id获取角色信息", notes = "已测试")
    @GetMapping(value = "/listAll")
    public ResultVO getListAll() {
        // 获取reids的key
        String key =
                CacheNameHelper.EP_ROLE_PREFIX_GETLIST;
        // 统一返回值
        List<EpRole> roles = null;
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            roles = epRoleService.getList();
            redisUtil.set(key, roles);
        }else {
            roles = (List<EpRole>) obj;
        }
        // 删除缓存
        return ResultVO.success(roles);
    }


    /**
     * 获取用户的所有角色
     * @return
     */
    @ApiOperation(value="获取用户的所有角色", notes="已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
    })
    @GetMapping("/list/{num}")
    public ResultVO getListByUserIdAndNum(
            @PathVariable @NotNull @Min(1) Integer num, HttpServletRequest request){
        EpUserDetails userByRequest = oauth2Util.getUserByRequest(request);
        Long userId = userByRequest.getUserId();
        // 获取reids的key
        String key = String.format(
                CacheNameHelper.EP_ROLE_PREFIX_GETLISTBYUSERIDANDNUM, userId, num);
        // 统一返回值
        PageInfo<EpRole> roles = null;
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            roles = epRoleService.selectByUserIdAndNum(userId, num);
            redisUtil.set(key, roles);
        }else {
            roles = (PageInfo<EpRole>) obj;
        }
        // 删除缓存
        return ResultVO.success(roles);
    }

    /**
     * 删除所有缓存
     * @return
     */
    @ApiOperation(value="删除所有缓存", notes="已测试")
    @GetMapping("/deleteAll")
    public ResultVO deleteAll(){
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_ROLE_PREFIX);
        return ResultVO.success();
    }




}
