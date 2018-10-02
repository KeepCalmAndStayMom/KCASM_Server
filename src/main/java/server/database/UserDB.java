package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public static boolean addUser(int homestation_id, String name, int age, int height, int weight, int medic_id) {
        final String sql = "INSERT INTO user(homestation_id, name, age, height, weight, medic_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, name);
            st.setInt(3, age);
            st.setInt(4, height);
            st.setInt(5, weight);
            st.setInt(6, medic_id);
            st.executeUpdate();
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String getUser(int homestation_id) {
        final String sql = "SELECT * FROM user WHERE homestation_id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);

            ResultSet rs = st.executeQuery();
            StringBuilder builder = new StringBuilder();

            builder.append("{\"name\": \"").append(rs.getString("name")).append("\",");
            builder.append("\"age\": ").append(rs.getInt("age")).append(",");
            builder.append("\"height\": ").append(rs.getInt("height")).append(",");
            builder.append("\"weight\": ").append(rs.getInt("weight")).append(",");
            builder.append("\"medic_id\": ").append(rs.getInt("medic_id")).append("}");

            conn.close();

            return builder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateUser(int homestation_id, String name, Integer age, Integer height, Integer weight) {
        final String sql = "UPDATE user SET name=?, age=?, height=?, weight=? WHERE homestation_id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, name);
            st.setInt(2, age);
            st.setInt(3, height);
            st.setInt(4, weight);
            st.setInt(5, homestation_id);

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
}
