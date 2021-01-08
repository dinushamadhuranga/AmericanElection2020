package com.iit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    //use to store DBConnection object and prevent creating objects of the DBConnection class
    private static DBConnection dbConnection;
    public static Connection connection;

    //get the database
    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/election2020" , "root" , "root");
    }

    //assing a object if dbconnection is null
    public static DBConnection getInstance() throws SQLException {
        return dbConnection == null ? dbConnection = new DBConnection() : dbConnection;
    }

    //return the connection of the dbconection object
    public Connection getConnection(){
        return connection;
    }


}
