package com.cybersoft.crm.controller;

import com.cybersoft.crm.dao.ProjectDao;
import com.cybersoft.crm.dao.TaskDao;
import com.cybersoft.crm.model.ProjectModel;
import com.cybersoft.crm.model.TaskModel;
import com.cybersoft.crm.variable.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {Variable.URL_HOME})
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskModel> taskList =  TaskDao.getAllTask();
        List<ProjectModel> projectList = ProjectDao.getAllProject();

        req.setAttribute("task_list", taskList.size());
        req.setAttribute("project_list", projectList.size());

        req.setAttribute("task_chua_bat_dau", TaskDao.getNumberOfStatus2(taskList,1));
        req.setAttribute("task_dang_thuc_hien", TaskDao.getNumberOfStatus2(taskList,2));
        req.setAttribute("task_da_hoan_thanh", TaskDao.getNumberOfStatus2(taskList,3));

        req.setAttribute("project_chua_bat_dau", ProjectDao.getNumberOfStatus(projectList,1));
        req.setAttribute("project_dang_thuc_hien", ProjectDao.getNumberOfStatus(projectList,2));
        req.setAttribute("project_da_hoan_thanh", ProjectDao.getNumberOfStatus(projectList,3));

        req.getRequestDispatcher("/home/index.jsp").forward(req, resp);
    }
}
