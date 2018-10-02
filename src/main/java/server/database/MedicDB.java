package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicDB {

    public static void addMedic(String name) {
        final String sql = "INSERT INTO medic(id, name) VALUES (null, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.executeUpdate();
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getMedic(int id) {
        final String sql = "SELECT * FROM medic WHERE id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            StringBuilder builder = new StringBuilder();
            builder.append("{\"name\": \"").append(rs.getString("name")).append("\"}");
            conn.close();
            return builder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateMedic(int id, String name) {
        final String sql = "UPDATE medic SET name=? WHERE id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.setInt(2, id);

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

    public static String getUsers(int id) {
        final String sql = "SELECT * FROM user WHERE medic_id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            String json = getMedicJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getMedicJson(ResultSet rs) throws SQLException {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        while (rs.next()) {
            builder.append("\"").append(rs.getInt("homestation_id")).append("\": {");
            builder.append("\"name\": \"").append(rs.getString("name")).append("\",");
            builder.append("\"age\": ").append(rs.getInt("age")).append(",");
            builder.append("\"height\": ").append(rs.getInt("height")).append(",");
            builder.append("\"weight\": ").append(rs.getInt("weight")).append("},");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("}");

        return builder.toString();
    }
}
