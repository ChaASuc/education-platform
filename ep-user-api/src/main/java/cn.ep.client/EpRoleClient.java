package cn.ep.client;

import cn.ep.bean.EpRole;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author deschen
 * @Create 2019/10/7
 * @Description
 * @Since 1.0.0
 */
@FeignClient(
        name = "ep-user",
        configuration = FeignConfig.class
)
public interface EpRoleClient {


    /**
     * 新增角色
     * @param role
     * @return
     */
    @ApiOperation(value="新增角色", notes="已测试")
    @ApiImplicitParam(name = "role", value = "角色实体", required = true, dataType = "EpRole")
    @PostMapping("")
    public ResultVO insert(@RequestBody @NotNull @Validated({Insert.class}) EpRole role);

    /**
     * 根据主键修改和逻辑删除角色
     * @param role
     * @return
     */
    @ApiOperation(value="根据主键修改和逻辑删除角色",notes = "已测试")
    @ApiImplicitParam(name="role", value = "角色实体类", dataType = "EpRole")
    @PutMapping("")
    public ResultVO update(@RequestBody @NotNull @Validated({Update.class}) EpRole role);



    @ApiOperation(value="根据部门id获取角色信息", notes = "已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "deptId",value = "部门id", required = true, paramType = "path"),
            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
    })
    @GetMapping(value = "/dept/{deptId}/{num}")
    public ResultVO getByDeptIdAndNum(
            @PathVariable @NotNull @Min(0) Long deptId,
            @PathVariable @NotNull @Min(1)Integer num);


    /**
     * 获取用户的所有角色
     * @return
     */
    @ApiOperation(value="获取用户的所有角色", notes="已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "userId",value = "用户id", required = true, paramType = "path"),
            @ApiImplicitParam(name= "num",value = "页码", required = true, paramType = "path")
    })
    @GetMapping("/list/{userId}/{num}")
    public ResultVO getListByUserIdAndNum(
            @PathVariable @NotNull @Min(0) Long userId,
            @PathVariable @NotNull @Min(1) Integer num);





}
