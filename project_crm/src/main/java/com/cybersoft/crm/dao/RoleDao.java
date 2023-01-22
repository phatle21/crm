package com.cybersoft.crm.dao;

import com.cybersoft.crm.connection.MySqlConnection;
import com.cybersoft.crm.model.RoleModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// roles ( id , role_name , description )
// role-add.jsp = role-edit.jsp = role-table.jsp
public class RoleDao {
    public static RoleModel getRoleById(int id) {
        try {
            String query = "SELECT * FROM roles WHERE id = ?";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                RoleModel role = new RoleModel();
                role.setId(result.getInt("id"));
                role.setRoleName(result.getString("role_name"));
                role.setDescription(result.getString("description"));

                return role;
            }
            connection.close();
        } catch (Exception e) {

        }
        return null;
    }

    public static List<RoleModel> getAllRole() {

        List<RoleModel> listRole = new ArrayList<RoleModel>();
        try {
            String query = "SELECT * FROM roles";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                RoleModel role = new RoleModel();
                role.setId(result.getInt("id"));
                role.setRoleName(result.getString("role_name"));
                role.setDescription(result.getString("description"));

                listRole.add(role);
            }
            connection.close();
        } catch (Exception e) {

        }
        return listRole;
    }

    public static void addNewRole(RoleModel role) {

        try {
            String query = "INSERT INTO roles (role_name, description) VALUES (?, ?)";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, role.getRoleName());
            statement.setString(2, role.getDescription());

            int result = statement.executeUpdate();

            if(result < 1) {
                System.out.println("Add fail");
            }
            connection.close();
        } catch (Exception e) {

            e.getStackTrace();
        }
    }

    public static void editRole(RoleModel role) {

        try {
            String query = "UPDATE roles SET role_name = ?, description = ? WHERE id = ?";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, role.getRoleName());
            statement.setString(2, role.getDescription());
            statement.setInt(3, role.getId());

            int result = statement.executeUpdate();

            if(result < 1) {
                System.out.println("Add fail");
            }
            connection.close();
        } catch (Exception e) {

        }
    }

    public static void removeRoleById(int id) {

        try {
            String query = "DELETE FROM roles WHERE id = ?";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            int result = statement.executeUpdate();
            if(result < 1) {
                System.out.println("Delete fail!");
            }
            connection.close();
        } catch (Exception e) {

        }
    }

    public static RoleModel getRoleByRoleName(String roleName) {
        try {
            String query = "SELECT * FROM roles WHERE role_name = ?";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, roleName);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                RoleModel role = new RoleModel();
                role.setId(result.getInt("id"));
                role.setRoleName(result.getString("role_name"));
                role.setDescription(result.getString("description"));

                return role;
            }
            connection.close();
        } catch (Exception e) {

        }
        return null;
    }


}
