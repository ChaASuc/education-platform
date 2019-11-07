package cn.ep.controller;

import cn.ep.bean.AuthToken;
import cn.ep.bean.EpUserDetails;
import cn.ep.constant.RoleConstant;
import cn.ep.constant.UserConstant;
import cn.ep.service.AuthService;
import cn.ep.utils.Oauth2Util;
import cn.ep.utils.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;


@RestController
@RequestMapping("/ep/auth")
@Validated
public class AuthController {

    @Value("${auth.clientId}")
    String clientId;
    @Value("${auth.clientSecret}")
    String clientSecret;
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    @Autowired
    private Oauth2Util oauth2Util;

    @Autowired
    AuthService authService;

    @ApiOperation(value="前台登入", notes = "已测试")
    @PostMapping("/front/login")
    public ResultVO FrontLogin(@RequestParam @NotBlank String username,
                          @RequestParam @NotBlank String password) {

        authService.checkRoleByUsernameAndType(username, UserConstant.TYPE_USER, RoleConstant.type_student);
        //申请令牌
        AuthToken authToken =  authService.login(username,password,clientId,clientSecret);

        return ResultVO.success(authToken);
    }

    @ApiOperation(value="后台登入", notes = "已测试")
    @PostMapping("/background/login")
    public ResultVO backgroundLogin(@RequestParam @NotBlank String username,
                          @RequestParam @NotBlank String password) {

        authService.checkRoleByUsernameAndType(username, UserConstant.TYPE_USER, RoleConstant.type_noStudent);
        //申请令牌
        AuthToken authToken =  authService.login(username,password,clientId,clientSecret);


        return ResultVO.success(authToken);
    }




    //退出
    @ApiOperation(value="登出", notes = "已测试")
    @GetMapping("/userlogout")
    public ResultVO logout(HttpServletRequest request) {
        //取出cookie中的用户身份令牌
        String token = oauth2Util.getTokenByRequest(request);
        //删除redis中的token
        authService.delToken(token);
        return ResultVO.success();
    }
//
    @GetMapping("/user")
    public ResultVO userJwt(HttpServletRequest request) {

        //拿身份令牌从redis中查询jwt令牌
        EpUserDetails userJwt = oauth2Util.getUserByRequest(request);
//        if(userToken!=null){
//            //将jwt令牌返回给用户
//            String jwt_token = userToken.getJwt_token();
//            return ResultVO.success(jwt_token);
//        }
        return ResultVO.success(userJwt);
    }

    @GetMapping("/hello")
    public ResultVO hello() {

        return ResultVO.success("你好");
    }

}
