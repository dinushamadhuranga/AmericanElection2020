package com.iit.dao;

import com.iit.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//this class is used to execute all the queries ..

public class CrudUtil {

    // the query and add the values and creating a preparedstatement  to execute
    private static PreparedStatement getPreparedStatement(String sql , Object... par) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        for (int i = 0; i < par.length; i++) {
            prepareStatement.setObject(i+1, par[i]);
        }
        return prepareStatement;
    }

    //execute the update and return the output of the database
    public static boolean executeUpdate(String sql , Object... fields) throws ClassNotFoundException, SQLException{
        return getPreparedStatement(sql, fields).executeUpdate()>0;
    }

    //executes the query and returns the result set from the database
    public static ResultSet executeQuary(String sql , Object... fields) throws ClassNotFoundException, SQLException{
        return getPreparedStatement(sql, fields).executeQuery();
    }
}
