package cn.ep.client;

import cn.ep.bean.EpUser;
import cn.ep.utils.ResultVO;
import cn.ep.validate.groups.Update;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/ep/user")
    public ResultVO insert(EpUser user);

    /**
     * 根据主键修改和逻辑删除用户
     * @param user
     * @return
     */
    @PutMapping("/ep/user")
    public ResultVO update(EpUser user);


    @GetMapping(value = "/ep/user")
    public ResultVO getByUserNicknameAnduserPwd(String userNickname, String userPwd);
}
