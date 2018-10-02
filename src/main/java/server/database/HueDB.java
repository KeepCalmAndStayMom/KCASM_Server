package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HueDB {

    public static boolean addHue(int homestation_id, String timedate, int cromosoft, int cromohard) {
        final String sql = "INSERT INTO hue(homestation_id, timedate, cromosoft, cromohard) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, timedate);
            st.setInt(3, cromosoft);
            st.setInt(4, cromohard);

            st.executeUpdate();
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public static String getHue(int homestation_id) {
        final String sql = "SELECT * FROM hue WHERE homestation_id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            String json = getHueJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getHueWithDate(int homestation_id, String date) {
        final String sql = "SELECT * FROM hue WHERE homestation_id=? AND timedate BETWEEN ? AND ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, date + " 00:00:00");
            st.setString(3, date + " 23:59:59");
            String json = getHueJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getHueWithDateInterval(int homestation_id, String startDatetime, String endDatetime) {
        final String sql = "SELECT * FROM hue WHERE homestation_id=? AND timedate BETWEEN ? AND ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2,  startDatetime.replace('T', ' '));
            if (endDatetime.contains("T"))
                st.setString(3, endDatetime.replace('T', ' '));
            else
                st.setString(3, endDatetime + " 23:59:59");
            String json = getHueJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getHueJson(ResultSet rs) throws SQLException {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        while (rs.next()) {
            builder.append("\"").append(rs.getString("timedate")).append("\": {");
            builder.append("\"cromosoft\": ").append(rs.getInt("cromosoft")).append(",");
            builder.append("\"cromohard\": ").append(rs.getInt("cromohard")).append("},");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("}");

        return builder.toString();
    }
}
