package server.database.v2;

import server.api.v2.Regex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FitbitDB {

    static Connection conn;

    static private List<Map<String, Object>> getListFitbit(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, Object> map;

        while(rs.next()) {
            map = new LinkedHashMap<>();
            map.put("patient_id", rs.getInt("patient_id"));
            map.put("timedate", rs.getString("timedate"));
            if(rs.getInt("avg_heartbeats") == 0)
                map.put("avg_heartbeats", null);
            else
                map.put("avg_heartbeats", rs.getInt("avg_heartbeats"));
            map.put("calories", rs.getInt("calories"));
            map.put("elevation", rs.getDouble("elevation"));
            map.put("floors", rs.getInt("floors"));
            map.put("steps", rs.getInt("steps"));
            map.put("distance", rs.getDouble("distance"));
            map.put("minutes_asleep", rs.getInt("minutes_asleep"));
            map.put("minutes_awake", rs.getInt("minutes_awake"));
            list.add(map);
        }
        conn.close();
        return list;
    }

    static public List<Map<String, Object>> selectDate(int patientId, String date) {

        final String sql = "SELECT * FROM Fitbit WHERE patient_id=? AND timedate LIKE ?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date + "%");

            return getListFitbit(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> selectDateInterval(int patientId, String startTimedate, String endTimedate) {

        String sql = "SELECT * FROM Fitbit WHERE patient_id=? AND timedate BETWEEN ? AND ?";

        if(startTimedate.matches(Regex.DATE_REGEX))
            startTimedate += "T00:00:00";
        if(endTimedate.matches(Regex.DATE_REGEX))
            endTimedate += "T23:59:59";

        System.out.println(startTimedate + " " + endTimedate);

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, startTimedate.replace("T", " "));
            st.setString(3, endTimedate.replace("T", " "));

            return getListFitbit(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> select(int patientId) {

        final String sql = "SELECT * FROM Fitbit WHERE patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);

            return getListFitbit(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    static public boolean insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Fitbit(patient_id, timedate, avg_heartbeats, calories, elevation, floors, steps, distance, minutes_sleep, minutes_awake) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("patient_id"));
            st.setString(2, String.valueOf(map.get("timedate")));
            st.setInt(3, (Integer) map.get("avg_heartbeats"));
            st.setInt(4, (Integer) map.get("calories"));
            st.setDouble(5, (Double) map.get("elevation"));
            st.setInt(6, (Integer) map.get("floors"));
            st.setInt(7, (Integer) map.get("steps"));
            st.setDouble(8, (Double) map.get("distance"));
            st.setInt(9, (Integer) map.get("minutes_asleep"));
            st.setInt(10, (Integer) map.get("minutes_awake"));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
