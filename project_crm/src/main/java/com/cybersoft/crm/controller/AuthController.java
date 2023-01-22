package com.cybersoft.crm.controller;

import com.cybersoft.crm.dao.AuthDao;
import com.cybersoft.crm.dao.MD5;
import com.cybersoft.crm.dao.RoleDao;
import com.cybersoft.crm.model.RoleModel;
import com.cybersoft.crm.model.UserModel;
import com.cybersoft.crm.module.User;
import com.cybersoft.crm.variable.Variable;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(urlPatterns = {Variable.URL_LOGIN,Variable.URL_LOGOUT})
public class AuthController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch(path){
            case Variable.URL_LOGIN:
                if(req.getSession().getAttribute(Variable.USER_LOGIN) != null) {
                    resp.sendRedirect(req.getContextPath() + Variable.URL_HOME);
                    return;
                }
                req.getRequestDispatcher("/authorization/login.jsp").forward(req, resp);
                break;
            case Variable.URL_LOGOUT:
                HttpSession session = req.getSession();
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + Variable.URL_LOGIN);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserModel user = AuthDao.login(email);
        String pass = MD5.getMd5(password);

        if (user == null || !pass.equals(user.getPassword())) {
            req.setAttribute("msg_error", "Email không tồn tại hoặc mật khẩu không chính xác!");
            req.getRequestDispatcher("/authorization/login.jsp").forward(req, resp);
            return;
        }
        RoleModel role = RoleDao.getRoleById(user.getRoleId());
        User userLogin = new User(user.getId(),user.getEmail(), user.getAddress()
                ,user.getPassword(),user.getFullName(),role.getRoleName(),role.getDescription(),user.getPhoneNumber());

        HttpSession session = req.getSession();
        session.setAttribute(Variable.USER_LOGIN, userLogin);
        session.setMaxInactiveInterval(6000);

        resp.sendRedirect(req.getContextPath() + Variable.URL_HOME);
    }
}