package cn.ep.client;

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
    @PostMapping("")
    ResultVO insert(@RequestBody @Validated({Insert.class}) EpUser user);

    /**
     * 根据主键修改和逻辑删除用户
     * @param user
     * @return
     */
    @ApiOperation(value="根据主键修改和逻辑删除用户",notes = "已测试")
    @ApiImplicitParam(name="user", value = "用户实体类", dataType = "EpUser")
    @PutMapping("")
    ResultVO update(@RequestBody @Validated({Update.class}) EpUser user);



    @ApiOperation(value="根据账号查询用户", notes = "已测试")
    @GetMapping(value = "")
    ResultVO getByUserNicknameAndType(
            @RequestParam @NotNull String userNickname, @RequestParam @NotNull Integer type);


    @ApiOperation(value="根据部门id获取用户信息", notes = "已测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name= "deptId",value = "部门id", required = true, paramType = "path")
    })
    @GetMapping(value = "/dept/{deptId}/{num}")
    ResultVO getByDeptIdAndNum(
            @PathVariable @NotNull @Min(0) Long deptId,
            @PathVariable @NotNull @Min(1)Integer num);


    /**
     * 删除所有缓存
     * @return
     */
    @ApiOperation(value="删除所有缓存", notes="已测试")
    @GetMapping("/deleteAll")
    ResultVO deleteAll();
}
