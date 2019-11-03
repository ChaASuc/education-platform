package cn.ep.config;

import cn.ep.utils.JsonUtil;
import cn.ep.utils.ResultVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 2018/5/24 0024.
 *
 * @author zlf
 * @email i@merryyou.cn
 * @since 1.0
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException, IOException {

        ResultVO failure = ResultVO.failure(HttpServletResponse.SC_UNAUTHORIZED, "该用户没有权限");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JsonUtil.obj2String(failure));
    }
}