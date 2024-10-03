package com.crud;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    @Getter
    private static final Connection conn;

    static {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = System.getenv("MYSQL_ROOT_PASSWORD");
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
