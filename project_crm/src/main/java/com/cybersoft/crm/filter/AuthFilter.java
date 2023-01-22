package com.cybersoft.crm.filter;

import com.cybersoft.crm.module.User;
import com.cybersoft.crm.variable.Variable;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        req.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        if(Variable.URL_LOGIN.equals(path)) {
            chain.doFilter(request, response);
            return;
        }
        User userLogin = (User) req.getSession().getAttribute(Variable.USER_LOGIN);

        if( userLogin == null) {
            resp.sendRedirect(req.getContextPath() + Variable.URL_LOGIN);
            return;
        }

        String roleName = userLogin.getRoleName();

        //chỉ admin mới đc vào quản lý role
        if(path.startsWith(Variable.URL_ROLE) && !Variable.ROLE_ADMIN.equals(roleName)) {
            resp.sendRedirect(req.getContextPath() + Variable.URL_ERROR_403);
            return;
        }
        //chỉ admin và leader mới được vào quản lý user, task và project
        if((path.startsWith(Variable.URL_USER) || path.startsWith(Variable.URL_TASK) ||
                path.startsWith(Variable.URL_PROJECT)) && !Variable.ROLE_ADMIN.equals(roleName)
                && !Variable.ROLE_LEADER.equals(roleName)) {
            resp.sendRedirect(req.getContextPath() + Variable.URL_ERROR_403);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}