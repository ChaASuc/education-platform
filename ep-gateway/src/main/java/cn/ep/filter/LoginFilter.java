package cn.ep.filter;

import cn.ep.bean.EpPermission;
import cn.ep.mapper.EpPermissionMapper;
import cn.ep.properties.EpUserProperties;
import cn.ep.service.AuthService;
import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Component
public class LoginFilter extends ZuulFilter {

    @Autowired
    AuthService authService;

    @Autowired
    private EpPermissionMapper epPermissionMapper;

    @Autowired
    private EpUserProperties epUserProperties;

    //过虑器的类型
    @Override
    public String filterType() {
        /*
         pre：请求在被路由之前执行
         routing：在路由请求时调用
         post：在routing和errror过滤器之后调用
         error：处理请求时发生错误调用
         */
        return "pre";
    }

    //过虑器序号，越小越被优先执行
    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        //得到request
        HttpServletRequest request = requestContext.getRequest();
        String requestURI = request.getRequestURI();

        List<EpPermission> epPermissions =
                epPermissionMapper.selectByRoleId(epUserProperties.getRole().getUserId());

        Integer requestMothod = getRequestMothod(request.getMethod());
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        long count = epPermissions.stream().filter(
                e -> {
                    return e.getReqMethod() == requestMothod &&
                            antPathMatcher.match(e.getZuulPrefix() + e.getServerPrefix(), requestURI);
                }
        ).count();
        if (count > 0) {
            return false;
        }
        //返回true表示要执行此过虑器
        return true;
    }

    private Integer getRequestMothod(String reqMethod) {
        if (reqMethod.equalsIgnoreCase("GET")) {
            return 1;
        } else if (reqMethod.equalsIgnoreCase("POST")) {
            return 2;
        } else if (reqMethod.equalsIgnoreCase("PUT")) {
                return 3;
        }if (reqMethod.equalsIgnoreCase("DELETE")) {
            return 4;
        }
        return null;
    }


    //过虑器的内容
    //测试的需求：过虑所有请求，判断头部信息是否有Authorization，如果没有则拒绝访问，否则转发到微服务。
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        //得到request
        HttpServletRequest request = requestContext.getRequest();
        //得到response
        HttpServletResponse response = requestContext.getResponse();
        //取cookie中的身份令牌
        if (!authService.getJwtFromHeader(request, response)) {
            return null;
        }

    }

}
