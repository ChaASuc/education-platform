package cn.ep.controller;

import cn.ep.bean.EpDept;
import cn.ep.bean.EpDeptDto;
import cn.ep.service.EpDeptService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @Author deschen
 * @Create 2019/10/22
 * @Description
 * @Since 1.0.0
 */
@Api(description = "部门模块")
@RestController
@RequestMapping("/ep/dept")
@Validated
public class EpDeptController {

    @Autowired
    private EpDeptService epDeptService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{

        // ep_category_prefix_getByUserNicknameAnduserPwd_{用户名}_{密码}
        public static final String EP_USER_PREFIX_GETBYUSERNICKNAMEANDUSERPWD =
                "ep_user_prefix_getByUserNicknameAnduserPwd_%s_%s";

        //ep_dept_prefix_* 用于全部删除，避免缓存
        public static final String EP_DEPT_PREFIX = "ep_dept_prefix_*";

        // ep_user_prefix_getByUserNicknameAndType_{用户名}_{用户名类型}
        public static final String EP_USER_GETBYUSERNICKNAMEANDTYPE =
                "ep_user_prefix_getByUserNicknameAndType_%s_%s";
        public static final String EP_DEPT_PREFIX_GETLISTALL =
                "ep_dept_prefix_getListAll";

    }

    /**
     * 新增部门
     * @param epDept
     * @return
     */
    @ApiOperation(value="新增部门", notes="已测试")
    @ApiImplicitParam(name = "epDept", value = "部门实体", required = true, dataType = "EpDept")
    @PostMapping("")
    public ResultVO insert(@RequestBody @Validated({Insert.class}) EpDept epDept){
        epDeptService.insert(epDept);
        // 清空相关缓存
        redisUtil.delFuz(EpUserController.CacheNameHelper.EP_USER_PREFIX);
        return ResultVO.success();
    }

    /**
     * 更新或逻辑删除部门
     * @param epDept
     * @return
     */
    @ApiOperation(value="更新或逻辑删除部门", notes="已测试")
    @ApiImplicitParam(name = "epDept", value = "部门实体", required = true, dataType = "EpDept")
    @PutMapping("")
    public ResultVO update(@RequestBody @Validated({Update.class}) EpDept epDept){
        epDeptService.update(epDept);
        // 清空相关缓存
        redisUtil.delFuz(EpUserController.CacheNameHelper.EP_USER_PREFIX);
        return ResultVO.success();
    }


    /**
     * 获取所有部门
     * @return
     */
    @ApiOperation(value="获取所有部门",notes = "已测试")
    @GetMapping("/listAll")
    public ResultVO getListAll(){

        // 获取reids的key
        String key = CacheNameHelper.EP_DEPT_PREFIX_GETLISTALL;
        // 统一返回值
        List<EpDeptDto> epDeptDtos = new ArrayList<>();
        // 查看是否有缓存
        Object obj = redisUtil.get(key);
        if (null == obj) {
            epDeptDtos = epDeptService.selectAll();
            redisUtil.set(key, epDeptDtos);
        }else {
            epDeptDtos = (List<EpDeptDto>) obj;
        }
        // 删除缓存
        return ResultVO.success(epDeptDtos);

    }







}
