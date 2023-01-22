package com.cybersoft.crm.controller;

import com.cybersoft.crm.dao.*;
import com.cybersoft.crm.model.TaskModel;
import com.cybersoft.crm.model.UserModel;
import com.cybersoft.crm.module.Task;
import com.cybersoft.crm.module.User;
import com.cybersoft.crm.variable.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {Variable.URL_PROFILE,Variable.URL_PROFILE_TASK, Variable.URL_PROFILE_TASK_UPDATE})
public class ProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userLogin = (User) req.getSession().getAttribute(Variable.USER_LOGIN);
        String path = req.getServletPath();
        switch (path) {
            case Variable.URL_PROFILE:
                req.setAttribute("user_edit", UserDao.getUserById(userLogin.getId()));
                req.getRequestDispatcher("/profile/profile-index.jsp").forward(req, resp);
                break;
            case Variable.URL_PROFILE_TASK:
                req.setAttribute("user_detail", UserDao.getUserById(userLogin.getId()));

                List<Task> taskListWithUserId = TaskDao.getAllTaskOfUserId(userLogin.getId());
                req.setAttribute("task_list", taskListWithUserId);

                req.setAttribute("task_chua_bat_dau", TaskDao.getNumberOfStatus(taskListWithUserId,1));
                req.setAttribute("task_dang_thuc_hien", TaskDao.getNumberOfStatus(taskListWithUserId,2));
                req.setAttribute("task_da_hoan_thanh", TaskDao.getNumberOfStatus(taskListWithUserId,3));

                req.getRequestDispatcher("/profile/profile-task.jsp").forward(req, resp);
                break;
            case Variable.URL_PROFILE_TASK_UPDATE:
                req.setAttribute("list_project", ProjectDao.getAllProject());
                TaskModel taskEdit = TaskDao.getTaskById(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("list_status", StatusDao.getAllStatus());
                req.setAttribute("list_user", ProjectDao.getUserByProjectId(taskEdit.getIdProject()));
                req.setAttribute("task_edit", taskEdit);
                req.getRequestDispatcher("/profile/profile-edit.jsp").forward(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        UserModel user;
        TaskModel task;
        switch (path) {
            case Variable.URL_PROFILE:
                user = new UserModel();
                user.setId(Integer.parseInt(req.getParameter("id")));
                user.setEmail(req.getParameter("email"));
                user.setFullName(req.getParameter("fullname"));

                if(req.getParameter("password") != "") {
                    String hasheds = MD5.getMd5(req.getParameter("password"));
                    user.setPassword(hasheds);
                }
                user.setAddress(req.getParameter("address"));
                user.setPhoneNumber(req.getParameter("phone"));

                ProfileDao.editUser(user);

                if(req.getParameter("password") != "") {
                    req.setAttribute("msg_error", "Vui lòng đăng nhập lại!");
                    resp.sendRedirect(req.getContextPath() + Variable.URL_LOGOUT);
                }else {
                    resp.sendRedirect(req.getContextPath() + Variable.URL_PROFILE);
                }
                break;
            case Variable.URL_PROFILE_TASK_UPDATE:
                task = new TaskModel();

                task.setId(Integer.parseInt(req.getParameter("id")));
                task.setTaskName(req.getParameter("task_name"));
                task.setIdUser(Integer.parseInt(req.getParameter("user_id")));
                task.setStartDate(req.getParameter("startdate"));
                task.setEndDate(req.getParameter("enddate"));
                task.setDescription(req.getParameter("description"));
                task.setIdStatus(Integer.parseInt(req.getParameter("status_id")));

                TaskDao.editTask(task);
                resp.sendRedirect(req.getContextPath() + Variable.URL_PROFILE_TASK);
                break;
            default:
                break;
        }
    }
}

