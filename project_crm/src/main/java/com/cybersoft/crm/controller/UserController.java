package com.cybersoft.crm.controller;

import com.cybersoft.crm.dao.MD5;
import com.cybersoft.crm.dao.RoleDao;
import com.cybersoft.crm.dao.TaskDao;
import com.cybersoft.crm.dao.UserDao;
import com.cybersoft.crm.model.TaskModel;
import com.cybersoft.crm.model.UserModel;
import com.cybersoft.crm.variable.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {Variable.URL_USER, Variable.URL_USER_ADD,Variable.URL_USER_EDIT,
        Variable.URL_USER_DELETE,Variable.URL_USER_DETAILS})
public class UserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case Variable.URL_USER_DETAILS:
                if(req.getParameter("id") == null) {
                    resp.sendRedirect(req.getContextPath() + Variable.URL_USER);
                    break;
                }
                req.setAttribute("user_detail", UserDao.getUserById(Integer.parseInt(req.getParameter("id"))));

                List<TaskModel> taskListWithUserId = TaskDao.getAllTaskModelOfUserId(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("task_list", taskListWithUserId);

                req.setAttribute("task_chua_bat_dau", TaskDao.getNumberOfStatus2(taskListWithUserId,1));
                req.setAttribute("task_dang_thuc_hien", TaskDao.getNumberOfStatus2(taskListWithUserId,2));
                req.setAttribute("task_da_hoan_thanh", TaskDao.getNumberOfStatus2(taskListWithUserId,3));
                req.getRequestDispatcher("/user/user-detail.jsp").forward(req, resp);
                break;
            case Variable.URL_USER:
                req.setAttribute("list_user", UserDao.getAllUser());
                req.getRequestDispatcher("/user/user-index.jsp").forward(req, resp);
                break;
            case Variable.URL_USER_ADD:
                req.setAttribute("list_role", RoleDao.getAllRole());
                req.getRequestDispatcher("/user/user-add.jsp").forward(req, resp);
                break;
            case Variable.URL_USER_EDIT:
                req.setAttribute("user_edit", UserDao.getUserById(Integer.parseInt(req.getParameter("id"))));
                req.setAttribute("list_role", RoleDao.getAllRole());
                req.getRequestDispatcher("/user/user-edit.jsp").forward(req, resp);
                break;
            case Variable.URL_USER_DELETE:
                UserDao.removeUserById(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + Variable.URL_USER);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        UserModel user;

        switch (path) {
            case Variable.URL_USER_ADD:
                user = new UserModel();

                user.setEmail(req.getParameter("email"));
                user.setFullName(req.getParameter("fullname"));

                String hashed = MD5.getMd5(req.getParameter("password"));

                user.setPassword(hashed);
                user.setRoleId(Integer.parseInt(req.getParameter("role")));
                user.setAddress(req.getParameter("address"));
                user.setPhoneNumber(req.getParameter("phone"));

                UserDao.addNewUser(user);

                resp.sendRedirect(req.getContextPath() + Variable.URL_USER);
                break;
            case Variable.URL_USER_EDIT:
                user = new UserModel();
                user.setId(Integer.parseInt(req.getParameter("id")));
                user.setEmail(req.getParameter("email"));
                user.setFullName(req.getParameter("fullname"));
                if(req.getParameter("password") != "") {
                    String hasheds = MD5.getMd5(req.getParameter("password"));

                    user.setPassword(hasheds);
                }
                user.setRoleId(Integer.parseInt(req.getParameter("role")));
                user.setAddress(req.getParameter("address"));
                user.setPhoneNumber(req.getParameter("phone"));

                UserDao.editUser(user);

                resp.sendRedirect(req.getContextPath() + Variable.URL_USER);
                break;
            default:
                break;
        }
    }
}
