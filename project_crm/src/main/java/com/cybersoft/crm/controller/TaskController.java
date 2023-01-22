package com.cybersoft.crm.controller;

import com.cybersoft.crm.dao.ProjectDao;
import com.cybersoft.crm.dao.StatusDao;
import com.cybersoft.crm.dao.TaskDao;
import com.cybersoft.crm.model.TaskModel;
import com.cybersoft.crm.module.User;
import com.cybersoft.crm.variable.Variable;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {Variable.URL_TASK,Variable.URL_TASK_ADD,Variable.URL_TASK_EDIT,
        Variable.URL_TASK_DELETE, Variable.URL_TASK_GET_USER,Variable.URL_TASK_DETAILS})
public class TaskController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        User userLogin = (User) req.getSession().getAttribute(Variable.USER_LOGIN);
        switch (path) {
            case Variable.URL_TASK_DETAILS:
                req.setAttribute("list_project", ProjectDao.getAllProject());
                TaskModel taskDetail = TaskDao.getTaskById(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("list_status", StatusDao.getAllStatus());
                req.setAttribute("list_user", ProjectDao.getUserByProjectId(taskDetail.getIdProject()));
                req.setAttribute("task_edit", taskDetail);
                req.getRequestDispatcher("/task/task-detail.jsp").forward(req, resp);
                break;
            case Variable.URL_TASK:
                if(userLogin.getRoleName().equals(Variable.ROLE_LEADER)) {
                    req.setAttribute("list_task", TaskDao.getAllTaskByLeader(userLogin.getId()));
                }else {
                    req.setAttribute("list_task", TaskDao.getAllTask());
                }
                req.getRequestDispatcher("/task/task-index.jsp").forward(req, resp);
                break;
            case Variable.URL_TASK_ADD:
                if(userLogin.getRoleName().equals(Variable.ROLE_LEADER)) {
                    req.setAttribute("list_project", ProjectDao.getAllProjectByUserId(userLogin.getId()));
                }else {
                    req.setAttribute("list_project", ProjectDao.getAllProject());
                }
                req.getRequestDispatcher("/task/task-add.jsp").forward(req, resp);
                break;
            case Variable.URL_TASK_EDIT:
                if(userLogin.getRoleName().equals(Variable.ROLE_LEADER)) {
                    req.setAttribute("list_project", ProjectDao.getAllProjectByUserId(userLogin.getId()));
                }else {
                    req.setAttribute("list_project", ProjectDao.getAllProject());
                }
                TaskModel taskEdit = TaskDao.getTaskById(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("list_status", StatusDao.getAllStatus());
                req.setAttribute("list_user", ProjectDao.getUserByProjectId(taskEdit.getIdProject()));
                req.setAttribute("task_edit", taskEdit);
                req.getRequestDispatcher("/task/task-edit.jsp").forward(req, resp);
                break;
            case Variable.URL_TASK_DELETE:
                TaskDao.deleteTask(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + Variable.URL_TASK);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        TaskModel task;
        resp.setCharacterEncoding("UTF-8");
        switch (path) {
            case Variable.URL_TASK_GET_USER:
                List<User> list = ProjectDao.getUserByProjectId(Integer.parseInt(req.getParameter("id")));
                PrintWriter out=resp.getWriter();
                resp.setContentType("application/json");
                Gson gson = new Gson();
                String objectToReturn="";
                objectToReturn = gson.toJson(list);
                out.write(objectToReturn); // Đưa Json trả về Ajax
                out.flush();
                return;
            case Variable.URL_TASK_ADD:
                task = new TaskModel();

                task.setTaskName(req.getParameter("task_name"));
                task.setIdProject(Integer.parseInt(req.getParameter("project")));
                task.setIdUser(Integer.parseInt(req.getParameter("user_id")));
                task.setStartDate(req.getParameter("startdate"));
                task.setEndDate(req.getParameter("enddate"));
                task.setDescription(req.getParameter("description"));
                task.setIdStatus(1); // mới tạo là chưa bắt đầu

                TaskDao.addTask(task);
                resp.sendRedirect(req.getContextPath() + Variable.URL_TASK);
                break;
            case Variable.URL_TASK_EDIT:
                task = new TaskModel();

                task.setId(Integer.parseInt(req.getParameter("id")));
                task.setTaskName(req.getParameter("task_name"));
                task.setIdUser(Integer.parseInt(req.getParameter("user_id")));
                task.setStartDate(req.getParameter("startdate"));
                task.setEndDate(req.getParameter("enddate"));
                task.setDescription(req.getParameter("description"));
                task.setIdStatus(Integer.parseInt(req.getParameter("status_id")));

                TaskDao.editTask(task);
                resp.sendRedirect(req.getContextPath() + Variable.URL_TASK);
                break;
            default:
                break;
        }
    }
}
