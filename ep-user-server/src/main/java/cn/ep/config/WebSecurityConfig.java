//package cn.ep.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//@Order(-1)
//class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private AjaxAccessDeniedHandler ajaxAccessDeniedHandler;
//
//    @Autowired
//    private CustomAccessDeniedHandler customAccessDeniedHandler;
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
////        web.ignoring().antMatchers("/ep/auth/front/login", "/ep/auth/background/login","/ep/auth/userlogout","/ep/auth/user");
//
//    }
////
////    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        return super.authenticationManagerBean();
////    }
////    //采用bcrypt对密码进行编码
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .httpBasic()
//                .and()
//                .formLogin()
//                .and()
//                .authorizeRequests()
//                .anyRequest()
////                .authenticated();
//                .access("@rbacauthorityservice.hasPermission(request,authentication)");
////
//        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler); // 无权访问 JSON 格式的数据
//    }
//
//
//}
