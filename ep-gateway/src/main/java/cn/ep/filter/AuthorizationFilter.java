package cn.ep.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component("AuthorizationFilter")
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求参数获取token
        String token = request.getParameter("token");
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("token", token);
        filterChain.doFilter(request, response);
    }

}
