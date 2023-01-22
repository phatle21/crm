package com.cybersoft.crm.dao;

import com.cybersoft.crm.connection.MySqlConnection;
import com.cybersoft.crm.model.StatusModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// 1 chưa bắt đầu
// 2 đang thực hiện
// 3 đã hoàn thành
// id , status_name
public class StatusDao {
    // dùng StatusModel hoặc Object đều được
    public static Object getStatusById(int id){
        try{
            String query = "select * from status where id = ?";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                StatusModel statusModel = new StatusModel();
                statusModel.setId(resultSet.getInt("id"));
                statusModel.setStatusName(resultSet.getString("status_name"));

                return statusModel;
            }
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // show data
    public static List<StatusModel> getAllStatus(){
        List<StatusModel> listStatus = new ArrayList<StatusModel>();
        try{
            String query = "select * from status";
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                StatusModel statusModel = new StatusModel();
                statusModel.setId(resultSet.getInt("id"));
                statusModel.setStatusName(resultSet.getString("status_name"));

                listStatus.add(statusModel);
            }
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return listStatus;
    }
}
