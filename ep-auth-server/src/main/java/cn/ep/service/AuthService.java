package cn.ep.service;

import cn.ep.bean.AuthToken;
import cn.ep.bean.EpUserDetails;
import cn.ep.client.EpUserClient;
import cn.ep.constant.RoleConstant;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import cn.ep.utils.JsonUtil;
import cn.ep.utils.ResultVO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AuthService {

    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private EpUserClient userClient;

    //用户认证申请令牌，将令牌存储到redis
    public AuthToken login(String username, String password, String clientId, String clientSecret) {

        //请求spring security申请令牌
        AuthToken authToken = this.applyToken(username, password, clientId, clientSecret);
        if (authToken == null) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "用户名或密码错误");
        }
        //用户身份令牌
        String access_token = authToken.getAccess_token();
        //存储到redis中的内容
        String jsonString = JSON.toJSONString(authToken);
        //将令牌存储到redis
        boolean result = this.saveToken(access_token, jsonString, tokenValiditySeconds);
        if (!result) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "存储令牌失败");
        }
        return authToken;

    }
    //存储到令牌到redis
    /**
     * @param access_token 用户身份令牌
     * @param content      内容就是AuthToken对象的内容
     * @param ttl          过期时间
     * @return
     */
    private boolean saveToken(String access_token, String content, long ttl) {
        String key = "user_token:" + access_token;
        stringRedisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire != null && expire > 0;
    }

    /**
     *从redis查询令牌
     * @param token 用户身份令牌
     * @return
     */
    public AuthToken getUserToken(String token){
        String key = "user_token:" + token;
        //从redis中取到令牌信息
        String value = stringRedisTemplate.opsForValue().get(key);
        //转成对象
        try {
            return JSON.parseObject(value, AuthToken.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除token
     * @param token 用户身份令牌
     * @return
     */
    public void delToken(String token){
        token = "user_token:" + token;
        Boolean success = stringRedisTemplate.delete(token);
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "删除token失败");
        }
    }

    //申请令牌
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret) {
        //从eureka中获取认证服务的地址（因为spring security在认证服务中）
        //从eureka中获取认证服务的一个实例的地址
        ServiceInstance serviceInstance = loadBalancerClient.choose("ep-auth");
        //此地址就是http://ip:port
        URI uri = serviceInstance.getUri();
        //令牌申请的地址 http://localhost:40400/oauth/token
        String authUrl = uri + "/oauth/token";
        //定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization", httpBasic);

        //定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);
        //String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables

        //设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });

        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);

        //申请令牌信息
        Map bodyMap = exchange.getBody();
        if (bodyMap == null ||
                bodyMap.get("access_token") == null ||
                bodyMap.get("refresh_token") == null ||
                bodyMap.get("jti") == null) {

            //解析spring security返回的错误信息
            if (bodyMap != null && bodyMap.get("error_description") != null) {
                String error_description = (String) bodyMap.get("error_description");
                if (error_description.contains("UserDetailsService returned null")) {
                    throw new GlobalException(GlobalEnum.OPERATION_ERROR, "账号不存在");
                } else if (error_description.contains("坏的凭证")) {
                    throw new GlobalException(GlobalEnum.OPERATION_ERROR, "账号密码错误");
                }
            }
            return null;
        }
        AuthToken authToken = new AuthToken();
        authToken.setAccess_token((String) bodyMap.get("jti"));//用户身份令牌
        authToken.setRefresh_token((String) bodyMap.get("refresh_token"));//刷新令牌
        authToken.setJwt_token((String) bodyMap.get("access_token"));//jwt令牌
        return authToken;
    }

    //获取httpBasic的串
    private String getHttpBasic(String clientId, String clientSecret) {
        String string = clientId + ":" + clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic " + new String(encode);
    }


    public void checkRoleByUsernameAndType(String username, Integer userType, Integer roleType) {
        ResultVO resultVO = userClient.getUserDetailsByUserNickNameAndType(username, userType);
        if (!resultVO.getCode().equals(resultVO.success().getCode())) {
            throw new GlobalException(GlobalEnum.EXIST_ERROR, "用户不存在");
        }
        EpUserDetails userDetails = JsonUtil.parseObject(resultVO.getData(), EpUserDetails.class);
        Boolean success = Optional.ofNullable(userDetails.getRoles())
                .map(roles -> {
                    AtomicBoolean atomicSuccess = new AtomicBoolean(false);
                    roles.forEach(role -> {
                        if (roleType.equals(RoleConstant.type_student)
                                && role.getRoleName().equalsIgnoreCase(RoleConstant.name_studnet)) {
                            atomicSuccess.set(true);
                        } else if (roleType.equals(RoleConstant.type_noStudent)
                                && (role.getRoleName().equalsIgnoreCase(RoleConstant.name_teacher)
                                || role.getRoleName().equalsIgnoreCase(RoleConstant.name_admin))) {
                            atomicSuccess.set(true);
                        }
                    });
                    return atomicSuccess.get();
                }).orElseThrow(() -> new GlobalException(GlobalEnum.EXIST_ERROR, "角色"));
        if (!success) {
            throw new GlobalException(GlobalEnum.OPERATION_ERROR, "请求接口错误");
        }
    }

}
