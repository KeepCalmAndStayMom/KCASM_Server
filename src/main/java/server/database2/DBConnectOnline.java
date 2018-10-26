package server.database2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectOnline {

    static private final String dbOnline = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7261794";
    static private final String user = "sql7261794";
    static private final String password = "LwVkxJwtFX";
    static private DBConnectOnline instance = null;

    private DBConnectOnline() {
        instance = this;
    }

    public static DBConnectOnline getInstance() {
        if (instance == null)
            return new DBConnectOnline();
        else {
            return instance;
        }
    }

    Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection(dbOnline, user, password);
        } catch (SQLException e) {
            throw new SQLException("Cannot get connection to " + dbOnline, e);
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}