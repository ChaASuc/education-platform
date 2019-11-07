package cn.ep.client;

import cn.ep.bean.EpPermission;
import cn.ep.bean.EpRole;
import cn.ep.bean.EpUser;
import cn.ep.utils.ResultVO;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author deschen
 * @Create 2019/9/15
 * @Description
 * @Since 1.0.0
 */
@FeignClient(
        name = "ep-user",
        configuration = FeignConfig.class
)
public interface EpUserClient {

    /**
     * 新增用户
     * @param user
     * @return
     */
    @ApiOperation(value="新增用户", notes="已测试")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "EpUser")
    @PostMapping("/ep/user")
    ResultVO insert(@RequestBody @Validated({Insert.class}) EpUser user);

    /**
     * 根据主键修改和逻辑删除用户
     * @param user
     * @return
     */
    @ApiOperation(value="根据主键修改和逻辑删除用户",notes = "已测试")
    @ApiImplicitParam(name="user", value = "用户实体类", dataType = "EpUser")
    @PutMapping("/ep/user")
    ResultVO update(@RequestBody @Validated({Update.class}) EpUser user);



    @ApiOperation(value="根据账号查询用户", notes = "已测试")
    @GetMapping(value = "/ep/user")
    ResultVO getUserByUserNicknameAndType(
            @RequestParam @NotNull String userNickname, @RequestParam @NotNull Integer type);


    @ApiOperation(value="根据部门id获取用户信息", notes = "已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "deptId",value = "部门id", required = true, paramType = "path")
    })
    @GetMapping("/ep/user/dept/{deptId}/{num}")
    ResultVO getUserByDeptIdAndNum(
            @PathVariable @NotNull @Min(0) Long deptId,
            @PathVariable @NotNull @Min(1) Integer num);

    @ApiOperation(value="根据部门id获取用户信息", notes = "已测试")
    @GetMapping(value = "/ep/user/userDetails")
    public ResultVO getUserDetailsByUserNickNameAndType(
            @RequestParam @NotNull String userNickname, @RequestParam @NotNull Integer type);
    /**
     * 删除所有缓存
     * @return
     */
    @ApiOperation(value="删除所有缓存", notes="已测试")
    @GetMapping("/ep/user/deleteAll")
    ResultVO deleteAll();

    /**
     * 新增角色
     * @param role
     * @return
     */
    @ApiOperation(value="新增角色", notes="已测试")
    @ApiImplicitParam(name = "role", value = "角色实体", required = true, dataType = "EpRole")
    @PostMapping("/ep/user/role")
    public ResultVO insert(@RequestBody @NotNull @Validated({Insert.class}) EpRole role);

    /**
     * 根据主键修改和逻辑删除角色
     * @param role
     * @return
     */
    @ApiOperation(value="根据主键修改和逻辑删除角色",notes = "已测试")
    @ApiImplicitParam(name="role", value = "角色实体类", dataType = "EpRole")
    @PutMapping("/ep/user/role")
    public ResultVO update(@RequestBody @NotNull @Validated({Update.class}) EpRole role);



    @ApiOperation(value="根据部门id获取角色信息", notes = "已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "deptId",value = "部门id", required = true, paramType = "path"),
            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
    })
    @GetMapping("/ep/user/role/dept/{deptId}/{num}")
    public ResultVO getRoleByDeptIdAndNum(
            @PathVariable @NotNull @Min(0) Long deptId,
            @PathVariable @NotNull @Min(1) Integer num);


    /**
     * 获取用户的所有角色
     * @return
     */
    @ApiOperation(value="获取用户的所有角色", notes="已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "userId",value = "用户id", required = true, paramType = "path"),
            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
    })
    @GetMapping("/ep/user/role/list/{userId}/{num}")
    public ResultVO getListByUserIdAndNum(
            @PathVariable @NotNull @Min(0) Long userId,
            @PathVariable @NotNull @Min(1) Integer num);


    /**
     * 新增权限
     * @param permission
     * @return
     */
    @ApiOperation(value="新增权限", notes="已测试")
    @ApiImplicitParam(name = "permission", value = "权限实体", required = true, dataType = "EpPermission")
    @PostMapping("/ep/user/perm")
    public ResultVO insert(@RequestBody @NotNull @Validated({Insert.class}) EpPermission permission);

    /**
     * 根据主键修改和逻辑删除权限
     * @param permission
     * @return
     */
    @ApiOperation(value="根据主键修改和逻辑删除权限",notes = "已测试")
    @ApiImplicitParam(name="permission", value = "权限实体类", dataType = "EpPermission")
    @PutMapping("/ep/user/perm")
    public ResultVO update(@RequestBody @NotNull @Validated({Update.class}) EpPermission permission);



    @ApiOperation(value="根据部门id获取权限信息", notes = "已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "deptId",value = "部门id", required = true, paramType = "path"),
            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
    })
    @GetMapping("/ep/user/perm/dept/{deptId}/{num}")
    public ResultVO getByDeptIdAndNum(
            @PathVariable @NotNull @Min(0) Long deptId,
            @PathVariable @NotNull @Min(1) Integer num);


    /**
     * 获取用户的所有权限
     * @return
     */
    @ApiOperation(value="获取角色的所有权限", notes="已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "roleId",value = "角色id", required = true, paramType = "path"),
            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
    })
    @GetMapping("/ep/user/perm/list/{roleId}/{num}")
    public ResultVO getListByRoleIdAndNum(
            @PathVariable @NotNull @Min(0) Long roleId,
            @PathVariable @NotNull @Min(1) Integer num);


}
