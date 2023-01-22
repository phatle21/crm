package com.cybersoft.crm.dao;

import com.cybersoft.crm.connection.MySqlConnection;
import com.cybersoft.crm.model.RoleModel;
import com.cybersoft.crm.model.UserModel;
import com.cybersoft.crm.module.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// bảng users
public class UserDao {

    public static List<User> getAllUser() {

        List<User> listUser = new ArrayList<User>();
        try {
            String query = "SELECT * FROM users";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                RoleModel role = RoleDao.getRoleById(result.getInt("role_id"));
                User user = new User(result.getInt("id"),result.getString("email"),result.getString("address"),
                        result.getString("password"),result.getString("full_name"),
                        role.getRoleName(), role.getDescription(),result.getString("phone_number"));

                listUser.add(user);
            }
            connection.close();
        } catch (Exception e) {

            e.printStackTrace();;
        }
        return listUser;
    }

    public static void addNewUser(UserModel user) {

        try {
            String query = "INSERT INTO users (email, password, full_name, address, phone_number, role_id) VALUES (?, ?, ?, ?, ?, ?)";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getPhoneNumber());
            statement.setInt(6, user.getRoleId());

            int result = statement.executeUpdate();

            if(result < 1) {
                System.out.println("Add user with email: " + user.getEmail() + " unseccessfully.");
            }
            connection.close();
        } catch (Exception e) {

        }
    }

    public static User getUserById(int id) {

        try {
            String query = "SELECT * FROM users WHERE id = ?";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                RoleModel role = RoleDao.getRoleById(result.getInt("role_id"));
                User user = new User(result.getInt("id"),result.getString("email"),result.getString("address"),
                        result.getString("password"),result.getString("full_name"),
                        role.getRoleName(), role.getDescription(),result.getString("phone_number"));

                return user;
            }
            connection.close();
        } catch (Exception e) {

        }
        return null;
    }

    public static void editUser(UserModel user) {

        try {
            String query;
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement;

            if(user.getPassword() != null) {
                query = "UPDATE users SET email = ?, full_name = ?, address = ?, phone_number = ?, role_id = ?, password = ? WHERE id = ?";

                statement = connection.prepareStatement(query);

                statement.setString(1, user.getEmail());
                statement.setString(2, user.getFullName());
                statement.setString(3, user.getAddress());
                statement.setString(4, user.getPhoneNumber());
                statement.setInt(5, user.getRoleId());
                statement.setString(6, user.getPassword());
                statement.setInt(7, user.getId());

                int result = statement.executeUpdate();

                if(result < 1) {
                    System.out.println("Edit fail!");
                }
            }else{
                query = "UPDATE users SET email = ?, full_name = ?, address = ?, phone_number = ?, role_id = ? WHERE id = ?";
                statement = connection.prepareStatement(query);

                statement.setString(1, user.getEmail());
                statement.setString(2, user.getFullName());
                statement.setString(3, user.getAddress());
                statement.setString(4, user.getPhoneNumber());
                statement.setInt(5, user.getRoleId());
                statement.setInt(6, user.getId());

                int result = statement.executeUpdate();

                if(result < 1) {
                    System.out.println("Edit fail!");
                }
            }

            connection.close();

        } catch (Exception e) {

        }
    }

    public static void removeUserById(int id) {

        try {
            String query = "DELETE FROM users WHERE id = ?";
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

    public static List<User> getUserByRole(String roleName) {

        List<User> listUser = new ArrayList<User>();
        try {
            RoleModel role = RoleDao.getRoleByRoleName(roleName);
            String query = "SELECT * FROM users WHERE role_id = ?";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, role.getId());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User(result.getInt("id"),result.getString("email"),result.getString("address"),
                        result.getString("password"),result.getString("full_name"),
                        role.getRoleName(), role.getDescription(),result.getString("phone_number"));

                listUser.add(user);
            }
            connection.close();
        } catch (Exception e) {

            e.printStackTrace();;
        }
        return listUser;
    }
}
