package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZWayDB {

    public static boolean addZWay(int homestation_id, String timedate, String movement, int temperature, int luminescence, int humidity) {
        final String sql = "INSERT INTO zway(homestation_id, timedate, movement, temperature, luminescence, humidity) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, timedate);
            if (movement.equalsIgnoreCase("on"))
                st.setInt(3, 1);
            else
                st.setInt(3, 0);
            st.setInt(4, temperature);
            st.setInt(5, luminescence);
            st.setInt(6, humidity);

            st.executeUpdate();

            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public static String getZWay(int homestation_id) {
        final String sql = "SELECT * FROM zway WHERE homestation_id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            String json = getZWayJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getZWayWithDate(int homestation_id, String date) {
        final String sql = "SELECT * FROM zway WHERE homestation_id=? AND timedate BETWEEN ? AND ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, date + " 00:00:00");
            st.setString(3, date + " 23:59:59");
            String json = getZWayJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getZWayWithDateInterval(int homestation_id, String startDatetime, String endDatetime) {
        final String sql = "SELECT * FROM zway WHERE homestation_id=? AND timedate BETWEEN ? AND ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2,  startDatetime.replace('T', ' '));
            if (endDatetime.contains("T"))
                st.setString(3, endDatetime.replace('T', ' '));
            else
                st.setString(3, endDatetime + " 23:59:59");
            String json = getZWayJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getZWayJson(ResultSet rs) throws SQLException {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        while (rs.next()) {
            builder.append("\"").append(rs.getString("timedate")).append("\": {");
            builder.append("\"movement\": ").append(rs.getBoolean("movement")).append(",");
            builder.append("\"temperature\": ").append(rs.getInt("temperature")).append(",");
            builder.append("\"luminescence\": ").append(rs.getInt("luminescence")).append(",");
            builder.append("\"humidity\":").append(rs.getInt("humidity")).append("},");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("}");

        return builder.toString();
    }
}