package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FitbitDB {

    public static boolean addFitbit(int homestation_id, String timedate, Integer avg_heartbeats, int calories, float elevation, int floors, int steps, double distance, int minutesAsleep, int minutesAwake) {
        final String sqlAll = "INSERT INTO fitbit(homestation_id, timedate, calories, elevation, floors, steps, distance, minutesAsleep, minutesAwake, avg_heartbeats) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final String sqlNull = "INSERT INTO fitbit(homestation_id, timedate, calories, elevation, floors, steps, distance, minutesAsleep, minutesAwake) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st;

            if (avg_heartbeats == null)
                st = conn.prepareStatement(sqlNull);
            else
                st = conn.prepareStatement(sqlAll);

            st.setInt(1, homestation_id);
            st.setString(2, timedate);
            st.setInt(3, calories);
            st.setFloat(4, elevation);
            st.setInt(5, floors);
            st.setInt(6, steps);
            st.setDouble(7, distance);
            st.setInt(8, minutesAsleep);
            st.setInt(9, minutesAwake);

            if (avg_heartbeats != null)
                st.setInt(10, avg_heartbeats);

            st.executeUpdate();

            conn.close();

        } catch(SQLException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public static String getFitbit(int homestation_id) {
        final String sql = "SELECT * FROM fitbit WHERE homestation_id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            String json = getFitbitJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getFitbitWithDate(int homestation_id, String date) {
        final String sql = "SELECT * FROM fitbit WHERE homestation_id=? AND timedate BETWEEN ? AND ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, date + " 00:00:00");
            st.setString(3, date + " 23:59:59");
            String json = getFitbitJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getFitbitWithInterval(int homestation_id, String startDatetime, String endDatetime) {
        final String sql = "SELECT * FROM fitbit WHERE homestation_id=? AND timedate BETWEEN ? AND ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2,  startDatetime.replace('T', ' '));
            if (endDatetime.contains("T"))
                st.setString(3, endDatetime.replace('T', ' '));
            else
                st.setString(3, endDatetime + " 23:59:59");
            String json = getFitbitJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getFitbitJson(ResultSet rs) throws SQLException {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        while (rs.next()) {
            builder.append("\"").append(rs.getString("timedate")).append("\": {");
            builder.append("\"avg_heartbeats\": ").append(rs.getObject("avg_heartbeats")).append(",");
            builder.append("\"calories\": ").append(rs.getInt("calories")).append(",");
            builder.append("\"elevation\": ").append(rs.getInt("elevation")).append(",");
            builder.append("\"floors\": ").append(rs.getInt("floors")).append(",");
            builder.append("\"steps\": ").append(rs.getInt("steps")).append(",");
            builder.append("\"distance\": ").append(rs.getInt("distance")).append(",");
            builder.append("\"minutesAsleep\": ").append(rs.getInt("minutesAsleep")).append(",");
            builder.append("\"minutesAwake\": ").append(rs.getInt("minutesAwake")).append("},");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("}");

        return builder.toString();
    }
}
