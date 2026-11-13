package com.rina.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DB {
    private static final String URL = System.getenv().getOrDefault("MATHWHIZ_DB_URL",
            "jdbc:mysql://localhost:3306/math_whiz_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    private static final String USER = System.getenv().getOrDefault("MATHWHIZ_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("MATHWHIZ_DB_PASSWORD", "root1234");
    public static Connection getConnection() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new SQLException("MySQL driver not found. Reload Maven and redeploy.", e); }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}