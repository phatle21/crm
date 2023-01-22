package com.cybersoft.crm.controller;


import com.cybersoft.crm.dao.ProjectDao;
import com.cybersoft.crm.dao.StatusDao;
import com.cybersoft.crm.dao.TaskDao;
import com.cybersoft.crm.dao.UserDao;
import com.cybersoft.crm.model.ProjectModel;
import com.cybersoft.crm.module.Task;
import com.cybersoft.crm.module.User;
import com.cybersoft.crm.variable.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {Variable.URL_PROJECT, Variable.URL_PROJECT_ADD,Variable.URL_PROJECT_EDIT,
        Variable.URL_PROJECT_DELETE,Variable.URL_PROJECT_DETAILS,Variable.URL_PROJECT_MANAGER,
        Variable.URL_PROJECT_MANAGER_DELETE})
public class ProjectController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case Variable.URL_PROJECT_DETAILS:
                List<Task> taskListWithProject = TaskDao.getAllTaskOfProject(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("task_list", taskListWithProject);

                req.setAttribute("task_chua_bat_dau", TaskDao.getNumberOfStatus(taskListWithProject,1));
                req.setAttribute("task_dang_thuc_hien", TaskDao.getNumberOfStatus(taskListWithProject,2));
                req.setAttribute("task_da_hoan_thanh", TaskDao.getNumberOfStatus(taskListWithProject,3));

                req.setAttribute("list_user_of_project", ProjectDao.getUserByProjectId(Integer.parseInt(req.getParameter("id"))));
                req.getRequestDispatcher("/project/project-detail.jsp").forward(req, resp);
                break;
            case Variable.URL_PROJECT:
                User userLogin = (User) req.getSession().getAttribute(Variable.USER_LOGIN);
                if(userLogin.getRoleName().equals(Variable.ROLE_LEADER)) {
                    req.setAttribute("list_project", ProjectDao.getAllProjectByUserId(userLogin.getId()));
                }else {
                    req.setAttribute("list_project", ProjectDao.getAllProject());
                }
                req.getRequestDispatcher("/project/project-index.jsp").forward(req, resp);
                break;
            case Variable.URL_PROJECT_ADD:
                req.setAttribute("list_project", ProjectDao.getAllProject());
                req.setAttribute("list_user", UserDao.getUserByRole(Variable.ROLE_LEADER));
                req.getRequestDispatcher("/project/project-add.jsp").forward(req, resp);
                break;
            case Variable.URL_PROJECT_EDIT:
                req.setAttribute("list_status", StatusDao.getAllStatus());
                req.setAttribute("list_user", UserDao.getUserByRole(Variable.ROLE_LEADER));
                req.setAttribute("project_edit", ProjectDao.getProjectById(Integer.parseInt(req.getParameter("id"))));
                req.getRequestDispatcher("/project/project-edit.jsp").forward(req, resp);
                break;
            case Variable.URL_PROJECT_DELETE:
                ProjectDao.removeProjectById(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + Variable.URL_PROJECT);
                break;
            case Variable.URL_PROJECT_MANAGER:
                // tiến hành xử lý những user chưa thuộc dự án trước
                List<User> listKQ = new ArrayList<User>();
                List<User> listUsers = UserDao.getUserByRole(Variable.ROLE_MEMBER);
                for (User user : listUsers) {
                    if(!ProjectDao.checkUserOfProject(Integer.parseInt(req.getParameter("id")), user.getId())) {
                        listKQ.add(user);
                    }
                }
                req.setAttribute("list_user", listKQ);
                req.setAttribute("list_user_of_project", ProjectDao.getUserByProjectId(Integer.parseInt(req.getParameter("id"))));
                req.setAttribute("id_project", Integer.parseInt(req.getParameter("id")));
                req.getRequestDispatcher("/project/project-admin.jsp").forward(req, resp);
                break;
            case Variable.URL_PROJECT_MANAGER_DELETE:
                int project_id = (Integer.parseInt(req.getParameter("id_project")));
                int user_id = (Integer.parseInt(req.getParameter("id_user")));

                ProjectDao.removeUserOfProject(project_id, user_id);
                resp.sendRedirect(req.getContextPath() + Variable.URL_PROJECT_MANAGER + "?id=" + project_id);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        ProjectModel pro;

        switch (path) {
            case Variable.URL_PROJECT_ADD:
                pro = new ProjectModel();

                pro.setProjectName(req.getParameter("project_name"));
                pro.setDescription(req.getParameter("project_des"));
                pro.setStartDate(req.getParameter("project_start"));
                pro.setEndDate(req.getParameter("project_end"));
                pro.setIdUser(Integer.parseInt(req.getParameter("project_user")));

                ProjectDao.addNewProject(pro);
                resp.sendRedirect(req.getContextPath() + Variable.URL_PROJECT);
                break;
            case Variable.URL_PROJECT_EDIT:
                pro = new ProjectModel();

                pro.setId(Integer.parseInt(req.getParameter("id")));
                pro.setProjectName(req.getParameter("project_name"));
                pro.setDescription(req.getParameter("project_des"));
                pro.setStartDate(req.getParameter("project_start"));
                pro.setEndDate(req.getParameter("project_end"));
                pro.setIdUser(Integer.parseInt(req.getParameter("project_user")));
                pro.setIdStatus(Integer.parseInt(req.getParameter("project_status")));

                ProjectDao.editProject(pro);
                resp.sendRedirect(req.getContextPath() + Variable.URL_PROJECT);
                break;
            case Variable.URL_PROJECT_MANAGER:
                int id_project = (Integer.parseInt(req.getParameter("id")));
                int id_user = (Integer.parseInt(req.getParameter("add_user")));

                ProjectDao.addUserOfProject(id_project, id_user);
                resp.sendRedirect(req.getContextPath() + Variable.URL_PROJECT_MANAGER + "?id=" + id_project);
                break;
            default:
                break;
        }
    }
}
