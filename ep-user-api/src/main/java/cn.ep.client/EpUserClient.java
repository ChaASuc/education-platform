//package cn.ep.client;
//
//import cn.ep.utils.ResultVO;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @Author deschen
// * @Create 2019/9/15
// * @Description
// * @Since 1.0.0
// */
//@FeignClient(
//        name = "ep-user",
//        configuration = FeignConfig.class
//)
//public interface EpUserClient {
//
//    /**
//     * 新增用户
//     * @param user
//     * @return
//     */
//    @PostMapping("/ep/user")
//    public ResultVO insert(EpUser user);
//
//    /**
//     * 根据主键修改和逻辑删除用户
//     * @param user
//     * @return
//     */
//    @PutMapping("/ep/user")
//    public ResultVO update(EpUser user);
//
//
//    @GetMapping(value = "/ep/user")
//    public ResultVO getByUserNicknameAnduserPwd(String userNickname, String userPwd);
//}
