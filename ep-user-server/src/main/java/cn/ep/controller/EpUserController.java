package cn.ep.controller;

import cn.ep.bean.EpUser;
import cn.ep.service.EpUserService;
import cn.ep.utils.RedisUtil;
import cn.ep.utils.ResultVO;
import cn.ep.validate.groups.Insert;
import cn.ep.validate.groups.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @Author deschen
 * @Create 2019/10/7
 * @Description
 * @Since 1.0.0
 */
@Api(description = "用户模块")
@RestController
@RequestMapping("/ep/user")
@Validated
public class EpUserController {

    @Autowired
    private EpUserService epUserService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{

        // ep_category_prefix_getByUserNicknameAnduserPwd_{用户名}_{密码}
        public static final String EP_USER_PREFIX_GETBYUSERNICKNAMEANDUSERPWD =
                "ep_user_prefix_getByUserNicknameAnduserPwd_%s_%s";

        //ep_user_prefix_* 用于全部删除，避免缓存
        public static final String EP_USER_PREFIX = "ep_user_prefix_*";

        // ep_user_prefix_getByUserNicknameAndType_{用户名}_{用户名类型}
        public static final String EP_USER_GETBYUSERNICKNAMEANDTYPE =
                "ep_user_prefix_getByUserNicknameAndType_%s_%s";
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @ApiOperation(value="新增用户", notes="未测试")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "EpUser")
    @PostMapping("")
    public ResultVO insert(@RequestBody @Validated({Insert.class}) EpUser user){
        System.out.println("xxx");
        epUserService.insert(user);
        // 清空相关缓存
        redisUtil.delFuz(CacheNameHelper.EP_USER_PREFIX);
        return ResultVO.success();
    }

    /**
     * 根据主键修改和逻辑删除用户
     * @param user
     * @return
     */
    @ApiOperation(value="根据主键修改和逻辑删除用户",notes = "未测试")
    @ApiImplicitParam(name="user", value = "用户实体类", dataType = "EpUser")
    @PutMapping("")
    public ResultVO update(@RequestBody @Validated({Update.class}) EpUser user){
        epUserService.update(user);
        // 删除缓存
        redisUtil.delFuz(CacheNameHelper.EP_USER_PREFIX);
        return ResultVO.success();

    }



    @ApiOperation(value="根据账号查询用户", notes = "未测试")
    @GetMapping(value = "")
    public ResultVO getByUserNicknameAndType(
            @RequestParam @NotNull String userNickname, @RequestParam @NotNull Integer type) {
        // 获取reids的key
        String key = String.format(
                CacheNameHelper.EP_USER_PREFIX_GETBYUSERNICKNAMEANDUSERPWD, userNickname, type);
        // 统一返回值
        EpUser user = null;
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            user = epUserService.getUserByAccountAndType(userNickname, type);
            redisUtil.set(key, user);
        }else {
            user = (EpUser) obj;
        }
        // 删除缓存
        return ResultVO.success(user);
    }


}
