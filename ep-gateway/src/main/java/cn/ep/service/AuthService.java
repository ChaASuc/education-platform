package cn.ep.service;

import cn.ep.bean.AuthToken;
import cn.ep.vo.ResultVO;
import com.alibaba.fastjson.JSON;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //从头取出jwt令牌
    public Object getJwtFromHeader(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        if (null == token) {
            access_denied(ResultVO.failure(10002, "token不存在"));
            return null;
        }
        token = "user_token:" + token;

        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization) || !authorization.contains("Bearer")) {
            access_denied(ResultVO.failure(10002, "access_token不存在"));
            return null;
        }
        //从Bearer 后边开始取出token
        String accessTokenFromRequest = authorization.substring(7);
        //从redis中取到令牌信息
        String authTokenFromRedis = stringRedisTemplate.opsForValue().get(token);
        if (null == authTokenFromRedis) {
            access_denied(ResultVO.failure(10001, "token失效"));
            return null;
        }

        Long expire = stringRedisTemplate.getExpire(token, TimeUnit.SECONDS);

        if (expire == null || expire < 0) {
            access_denied(ResultVO.failure(10001, "token过期"));
            return null;
        }

        //转成对象
        AuthToken authToken = JSON.parseObject(authTokenFromRedis, AuthToken.class);
        String accessToken = authToken.getJwt_token();

        if (!accessTokenFromRequest.equals(accessToken)) {
            access_denied(ResultVO.failure(10001, "令牌不匹配"));
            return null;
        }

        return null;

    }


    //拒绝访问
    private void access_denied(ResultVO resultVO){
        RequestContext requestContext = RequestContext.getCurrentContext();
        //得到response
        HttpServletResponse response = requestContext.getResponse();
        //拒绝访问
        requestContext.setSendZuulResponse(false);
        //设置响应代码
        requestContext.setResponseStatusCode(200);

        //转成json
        String jsonString = JSON.toJSONString(resultVO);
        requestContext.setResponseBody(jsonString);
        //转成json，设置contentType
        response.setContentType("application/json;charset=utf-8");

    }
}
