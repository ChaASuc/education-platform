//package cn.ep.security;
//
//import cn.ep.utils.ResultVO;
//import com.alibaba.fastjson.JSON;
//import cn.ep.Enums.ResultEnum;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author: zzx
// * @date: 2018/10/15 15:31
// * @description: 用户登录失败时返回给前端的数据
// */
//@Component
//public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        httpServletResponse.getWriter().write(JSON.toJSONString(ResultVO.failure(ResultEnum.USER_LOGIN_FAILED)));
//    }
//
//}
