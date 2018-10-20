package server.database2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect2 {

    static private final String dbOnline = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7261794";
    static private final String user = "sql7261794";
    static private final String password = "LwVkxJwtFX";
    static private DBConnect2 instance2 = null;

    private DBConnect2() {
        instance2 = this;
    }

    public static DBConnect2 getInstance() {
        if (instance2 == null)
            return new DBConnect2();
        else {
            return instance2;
        }
    }

    Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection(dbOnline, user, password);
        } catch (SQLException e) {
            throw new SQLException("Cannot get connection to " + dbOnline, e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}