package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public static boolean addUser(int homestation_id, String name, int age, int height, int weight, int medic_id, String home_address, String hospital_address, String phone_number) {
        final String sql = "INSERT INTO user(homestation_id, name, age, height, weight, medic_id, home_address, hospital_address, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, name);
            st.setInt(3, age);
            st.setInt(4, height);
            st.setInt(5, weight);
            st.setInt(6, medic_id);
            st.setString(7, home_address);
            st.setString(8, hospital_address);
            st.setString(9, phone_number);
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
            builder.append("\"medic_id\": ").append(rs.getInt("medic_id")).append(",");
            builder.append("\"home_address\": \"").append(rs.getString("home_address")).append("\",");
            builder.append("\"hospital_address\": \"").append(rs.getString("hospital_address")).append("\",");
            builder.append("\"phone_number\": \"").append(rs.getString("phone_number")).append("\"}");

            conn.close();

            return builder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateUser(int homestation_id, String name, Integer age, Integer height, Integer weight, String home_address, String hospital_address, String phone_number) {
        final String sql = "UPDATE user SET name=?, age=?, height=?, weight=?, home_address=?, hospital_address=?, phone_number WHERE homestation_id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, name);
            st.setInt(2, age);
            st.setInt(3, height);
            st.setInt(4, weight);
            st.setInt(5, homestation_id);
            st.setString(6, home_address);
            st.setString(7, hospital_address);
            st.setString(8, phone_number);

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
