package com.cybersoft.crm.dao;

import com.cybersoft.crm.connection.MySqlConnection;
import com.cybersoft.crm.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDao {
    // static là lưu trên ram
    public static UserModel login(String email){
       UserModel userModel = null;
        try{
            String query = "SELECT * FROM users WHERE email = ?";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                userModel = new UserModel();
                userModel.setId(result.getInt("id"));
                userModel.setEmail(result.getString("email"));
                userModel.setPassword(result.getString("password"));
                userModel.setFullName(result.getString("full_name"));
                userModel.setAddress(result.getString("address"));
                userModel.setRoleId(result.getInt("role_id"));
                userModel.setPhoneNumber(result.getString("phone_number"));
            }
            connection.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return userModel;
    }
}
