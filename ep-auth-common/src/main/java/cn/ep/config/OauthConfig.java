package cn.ep.config;

import cn.ep.utils.Oauth2Util;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class OauthConfig {

    @Bean("oauth2Util")
    @SuppressWarnings("unchecked")
    @ConditionalOnMissingBean(name = "oauth2Util")
    public Oauth2Util oauth2Util(StringRedisTemplate stringRedisTemplate) {
        Oauth2Util oauth2Util = new Oauth2Util();
        oauth2Util.setStringRedisTemplate(stringRedisTemplate);

        return oauth2Util;
    }

//    @Bean("bCryptPasswordEncoder")
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
