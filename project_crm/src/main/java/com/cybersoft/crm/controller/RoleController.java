package com.cybersoft.crm.controller;

import com.cybersoft.crm.dao.RoleDao;
import com.cybersoft.crm.model.RoleModel;
import com.cybersoft.crm.variable.Variable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {Variable.URL_ROLE, Variable.URL_ROLE_ADD,Variable.URL_ROLE_EDIT,Variable.URL_ROLE_DELETE})
public class RoleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case Variable.URL_ROLE:
                req.setAttribute("list_role", RoleDao.getAllRole());
                // req.getRequestDispatcher("views/role/index.jsp").forward(req, resp);
                req.getRequestDispatcher("/role/role-index.jsp").forward(req, resp);
                break;
            case Variable.URL_ROLE_ADD:
                req.getRequestDispatcher("/role/role-add.jsp").forward(req, resp);
                break;
            case Variable.URL_ROLE_EDIT:
                req.setAttribute("role_edit", RoleDao.getRoleById(Integer.parseInt(req.getParameter("id"))));
                req.getRequestDispatcher("/role/role-edit.jsp").forward(req, resp);
                break;
            case Variable.URL_ROLE_DELETE:
                RoleDao.removeRoleById(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + Variable.URL_ROLE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        RoleModel role;

        switch (path) {
            case Variable.URL_ROLE_ADD:
                role = new RoleModel();

                role.setRoleName(req.getParameter("rolename"));
                role.setDescription(req.getParameter("description"));

                RoleDao.addNewRole(role);

                resp.sendRedirect(req.getContextPath() + Variable.URL_ROLE);
                break;
            case Variable.URL_ROLE_EDIT:
                role = new RoleModel();

                role.setId(Integer.parseInt(req.getParameter("id")));
                role.setRoleName(req.getParameter("rolename"));
                role.setDescription(req.getParameter("description"));

                RoleDao.editRole(role);

                resp.sendRedirect(req.getContextPath() + Variable.URL_ROLE);
                break;
            default:
                break;
        }
    }
}
