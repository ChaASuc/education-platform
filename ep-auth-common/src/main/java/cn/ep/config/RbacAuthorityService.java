//package cn.ep.config;
//
//import cn.ep.enums.GlobalEnum;
//import cn.ep.exception.GlobalException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.http.HttpServletRequest;
//import java.security.Permission;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @author: deschen
// * @date: 2018/10/16 9:54
// * @description: 权限访问控制
// */
//@Slf4j
//@Component("rbacauthorityservice")
//public class RbacAuthorityService {
//
//
//    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
//
//        Object userInfo = authentication.getPrincipal();
//
//        //请求方式
//        Integer method = getRequestMethod(request.getMethod());
//
//        boolean hasPermission  = false;
//
//        Set<String> permissions = new HashSet<>();
//        permissions.add("/ep/auth/front/login");
//        permissions.add("/ep/auth/background/login");
//        permissions.add("/ep/auth/userlogout");
//        permissions.add("/ep/auth/user");
//        permissions.add("/oauth/token/**");
//        permissions.add("/ep/user/**");
//
//        AntPathMatcher antPathMatcher = new AntPathMatcher();
//        String requestURI = request.getRequestURI();
//        long count = permissions.stream()
//                .filter(e -> {
//                         return antPathMatcher.match(e, request.getRequestURI());
//                }).count();
//
//        if(count > 0){
//            log.info("【安全框架】拦截成功链接 url = {}", request.getRequestURI());
//            hasPermission = true;
//        }
//
//        return hasPermission;
//    }
//
//    public Integer getRequestMethod(String method) {
//
//        if (null != method) {
//            if ("GET".equalsIgnoreCase(method)) {
//                return 1;
//            }else if ("POST".equalsIgnoreCase(method)) {
//                return 2;
//            }else if ("PUT".equalsIgnoreCase(method)) {
//                return 3;
//            }else if ("DELETE".equalsIgnoreCase(method)) {
//                return 4;
//            }else {
//                throw new GlobalException(GlobalEnum.PARAMS_ERROR.getCode(), "请求方式");
//            }
//        }else {
//            throw new GlobalException(GlobalEnum.PARAMS_ERROR.getCode(), "请求方式");
//        }
//
//    }
//}
