package com.youcode.crudapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    //class connection to database
    //static Connection connect = null;

    public Connection connect() throws SQLException {

        System.out.println("connected!");
        return DriverManager.getConnection("jdbc:sqlite:database.db");
    }
}
