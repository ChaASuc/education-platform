package cn.ep.utils;

import cn.ep.bean.AuthToken;
import cn.ep.bean.EpUserDetails;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Oauth2Util {

    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;

    private StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public EpUserDetails getUserByRequest(HttpServletRequest request) {
        String token = getTokenByRequest(request);
        token = "user_token:" + token;
        String accessTokenFromRequest = getAccessTokenByRequest(request);
        //从redis中取到令牌信息
        String authTokenFromRedis = stringRedisTemplate.opsForValue().get(token);
        if (null == authTokenFromRedis) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "token失效");
        }

        Long expire = stringRedisTemplate.getExpire(token, TimeUnit.SECONDS);

        if (expire == null || expire < 0) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "token过期");
        }



        //转成对象
        AuthToken authToken = JSON.parseObject(authTokenFromRedis, AuthToken.class);
        String accessToken = authToken.getJwt_token();

        if (!accessTokenFromRequest.equals(accessToken)) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "令牌不匹配");
        }

        Jwt jwt = JwtHelper.decode(accessToken);
        String claims = jwt.getClaims();
        EpUserDetails epUserDetails = JSON.parseObject(claims, EpUserDetails.class);
        Map map = JSON.parseObject(claims, Map.class);
        epUserDetails.setUserName((String) map.get("name"));
        epUserDetails.setUserNickname((String) map.get("username"));

        stringRedisTemplate.delete(token);
        stringRedisTemplate.boundValueOps(token).set(authTokenFromRedis, tokenValiditySeconds, TimeUnit.SECONDS);
        return epUserDetails;
    }

    // 获取token
    public String getTokenByRequest(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (null == token) {
            throw new GlobalException(GlobalEnum.EXIST_ERROR, "token不存在");
        }

        return token;
    }

    // 获取accessToken
    public String getAccessTokenByRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization) || !authorization.contains("Bearer")) {
            throw new GlobalException(GlobalEnum.PARAMS_ERROR, "access_token不存在");
        }
        //从Bearer 后边开始取出token
        String access_token = authorization.substring(7);
        return access_token;
    }
}
