package server.database.v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectOnline {

    static private final String dbOnline = "jdbc:mysql://2.229.216.18:23306/KCASM_Database";
    static private final String user = "kcasm";
    static private final String password = "tulliocinghiale";
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