package cn.ep.client;//package cn.ep.client;
//
//import cn.ep.bean.EpPermission;
//import cn.ep.utils.ResultVO;
//import cn.ep.validate.groups.Insert;
//import cn.ep.validate.groups.Update;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;
//
///**
// * @Author deschen
// * @Create 2019/10/7
// * @Description
// * @Since 1.0.0
// */
//@FeignClient(
//        name = "ep-user",
//        configuration = FeignConfig.class
//)
//public interface EpPermissionClient {
//
//
//    /**
//     * 新增权限
//     * @param permission
//     * @return
//     */
//    @ApiOperation(value="新增权限", notes="已测试")
//    @ApiImplicitParam(name = "permission", value = "权限实体", required = true, dataType = "EpPermission")
//    @PostMapping("")
//    public ResultVO insert(@RequestBody @NotNull @Validated({Insert.class}) EpPermission permission);
//
//    /**
//     * 根据主键修改和逻辑删除权限
//     * @param permission
//     * @return
//     */
//    @ApiOperation(value="根据主键修改和逻辑删除权限",notes = "已测试")
//    @ApiImplicitParam(name="permission", value = "权限实体类", dataType = "EpPermission")
//    @PutMapping("")
//    public ResultVO update(@RequestBody @NotNull @Validated({Update.class}) EpPermission permission);
//
//
//
//    @ApiOperation(value="根据部门id获取权限信息", notes = "已测试")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name= "deptId",value = "部门id", required = true, paramType = "path"),
//            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
//    })
//    @GetMapping(value = "/dept/{deptId}/{num}")
//    public ResultVO getByDeptIdAndNum(
//            @PathVariable @NotNull @Min(0) Long deptId,
//            @PathVariable @NotNull @Min(1)Integer num);
//
//
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
//            @PathVariable @NotNull @Min(1) Integer num);
//
//
//
//}
