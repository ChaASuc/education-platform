//package cn.ep.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//@Configuration
//@EnableResourceServer
////@Component
//public class CustomResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
////    @Autowired
////    private AjaxAccessDeniedHandler accessDeniedHandler;
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
//    }
//}