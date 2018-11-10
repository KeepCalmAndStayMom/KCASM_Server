package server.database.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDB {

    public static String getLogin(String name, String password) {
        final String sqlEmail = "SELECT * FROM login WHERE email=? AND password=?";
        final String sqlUser = "SELECT * FROM login WHERE username=? AND password=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sqlEmail);
            st.setString(1, name);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            String json = getLoginJson(rs);
            if (!json.equalsIgnoreCase("{}"))
                return json;

            //conn = DBConnectOnline.getInstance().getConnection();//vedere se è possibile togliere questa riga
            st = conn.prepareStatement(sqlUser);
            st.setString(1, name);
            st.setString(2, password);
            rs = st.executeQuery();
            json = getLoginJson(rs);
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getLoginJson(ResultSet rs) throws SQLException {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        while (rs.next()) {
            builder.append("\"username\": \"").append(rs.getString("username")).append("\", ");
            builder.append("\"type\": \"").append(rs.getString("type")).append("\",");
            builder.append("\"id\": ").append(rs.getInt("id")).append(",");
            builder.append("\"email\": \"").append(rs.getString("email")).append("\"");
        }
        builder.append("}");

        return builder.toString();
    }

    public static boolean updatePassword(String username, String password) {
        final String sql = "UPDATE login SET password=? WHERE username=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, username);

            if(st.executeUpdate() == 0) {
                conn.close();
                return false;
            }

            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean updateLogin(String old_username, String username, String email) {
        final String sql = "UPDATE login SET username=?, email=? WHERE username=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, email);
            st.setString(3, old_username);

            if(st.executeUpdate() == 0) {
                conn.close();
                return false;
            }

            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String getPassword(String name) {
        final String sql = "SELECT username, email, password FROM login WHERE email=? OR username=?";

        try{
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, name);
            ResultSet rs = st.executeQuery();
            if(rs.next())
                return "{ \"password\": \"" + rs.getString("password")
                        + "\", \"email\": \"" + rs.getString("email")
                        + "\", \"username\": \"" + rs.getString("username") + "\" }";
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}