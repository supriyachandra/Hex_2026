package com.car.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    private Connection conn;

    private static final DBConnectionUtil dbConnectionUtil;

    static{
        dbConnectionUtil= new DBConnectionUtil();
    }

    private DBConnectionUtil(){}

    public static DBConnectionUtil getInstance(){
        return dbConnectionUtil;
    }

    public Connection dbConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/hex_car_rental", "root", "1234");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public void dbClose(){
        try{
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
