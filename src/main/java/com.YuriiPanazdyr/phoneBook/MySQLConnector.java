package com.YuriiPanazdyr.phoneBook;

import java.sql.*;

public class MySQLConnector {
    private  final String driverName = "com.mysql.jdbc.Driver";
    private  final String URL = "jdbc:mysql://localhost:3306/phone_book?autoReconnect=true&useSSL=false";
    private  final String USER = "root";
    private  final String PASS = "root";
    private  Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public MySQLConnector() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException ex) {
            System.err.println("|||||||||||||||||||||||||||||ERROR||||||||||||||||||||||||||||" +
                             "\n||||||||||||||||||||Oops! Driver not found.|||||||||||||||||||");
            ex.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            System.err.println("|||||||||||||||||||||||||||||ERROR||||||||||||||||||||||||||||" +
                             "\n|||||||||||||Oops! Invalid URL, username or password.|||||||||");
            ex.printStackTrace();
        }
    }

    public void closeConnection (){
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println("|||||||||||||||||||||||||||||ERROR||||||||||||||||||||||||||||" +
                             "\n|||||||Oops! Could not close connection to the database.||||||");
            ex.printStackTrace();
        }
    }
}
