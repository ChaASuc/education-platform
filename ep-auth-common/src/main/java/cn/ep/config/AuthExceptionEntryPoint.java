package cn.ep.config;

import cn.ep.utils.JsonUtil;
import cn.ep.utils.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2018/5/24 0024.
 *
 * @author zlf
 * @email i@merryyou.cn
 * @since 1.0
 */
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException, IOException {

            Map<String, Object> map = new HashMap<>();
            Throwable cause = authException.getCause();

            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            try {
                if(cause instanceof InvalidTokenException) {
                    response.getWriter().write(
                            JsonUtil.parseObject(ResultVO.failure(HttpServletResponse.SC_UNAUTHORIZED, "token失效"))
                    );
                }else{
                    response.getWriter().write(
                            JsonUtil.parseObject(ResultVO.failure(HttpServletResponse.SC_UNAUTHORIZED, "token不存在"))
                    );

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}