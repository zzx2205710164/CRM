package com.zzx.crm.web.filter;

import com.zzx.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("进入到验证有没有登录过过滤器");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String path = httpServletRequest.getServletPath();
        if("/login.jsp".equals(path)||"/settings/user/login.do".equals(path)){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            HttpSession session =httpServletRequest.getSession();
            User user = (User) session.getAttribute("user");

            //如果user不为空，说明user登录过
            if (user!=null){
                filterChain.doFilter(servletRequest,servletResponse);
                //没有登录过
            }else{
                //重定向到登录页
            /*
                重定向的路径怎么写
                在实际项目开发中，对于路径的使用，不论操作的是前端还是后端，应该一样使用绝对路径
                关于转发和重定向的路径的写法如下
                转发：
                    使用的是一种特殊的绝对路径的方式，这种绝对路径前面不加/项目名，这种路径称为内部路径
                    /login.jsp
                重定向：
                    使用的是传统绝对路径的写法，前面必须已/项目名开头，后面跟具体的资源路径
                    /crm/login.jsp


                为什么使用重定向，使用转发不行嘛？
                    转发之后，路径停留在老路径上，而不是跳转之后最新资源的路径
                    我们应该在为用户跳转到登录页的同时，将浏览器的地址自动设置当前登录页的路径

             */
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login.jsp");

            }


        }
        }



    public void destroy() {

    }
}
