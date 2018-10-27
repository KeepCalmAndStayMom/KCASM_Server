package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FitbitDB {

    static Connection conn;

    static private LinkedList<LinkedHashMap<String, Object>> getListFitbit(ResultSet rs) throws SQLException {
        LinkedList<LinkedHashMap<String, Object>> list = new LinkedList<>();
        LinkedHashMap<String, Object> map;

        while(rs.next()) {
            map = new LinkedHashMap<>();
            map.put("Patient_id", rs.getInt("Patient_id"));
            map.put("timedate", rs.getString("timedate"));
            map.put("avg_heartbeats", rs.getInt("avg_heartbeats"));
            map.put("calories", rs.getInt("calories"));
            map.put("elevation", rs.getDouble("elevation"));
            map.put("floors", rs.getInt("floors"));
            map.put("steps", rs.getInt("steps"));
            map.put("distance", rs.getDouble("distance"));
            map.put("minutes_sleep", rs.getInt("minutes_sleep"));
            map.put("minutes_awake", rs.getInt("minutes_awake"));
            list.add(map);
        }

        return list;
    }

    static public LinkedList<LinkedHashMap<String, Object>> selectDate(int patientId, String date) {

        final String sql = "SELECT * FROM Fitbit WHERE Patient_id=? AND timedate LIKE ?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date);

            return getListFitbit(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public LinkedList<LinkedHashMap<String, Object>> selectDateInterval(int patientId, String startTimedate, String endTimedate) {

        final String sql = "SELECT * FROM Fitbit WHERE Patient_id=? AND timedate BETWEEN ? AND ?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, startTimedate);
            st.setString(3, endTimedate);

            return getListFitbit(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public LinkedList<LinkedHashMap<String, Object>> select(String patientId) {

        final String sql = "SELECT * FROM Fitbit WHERE Patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, patientId);

            return getListFitbit(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    static public boolean insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Fitbit(Patient_id, timedate, avg_heartbeats, calories, elevation, floors, steps, distance, minutes_sleep, minutes_awake) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("Patient_id"));
            st.setString(2, String.valueOf(map.get("timedate")));
            st.setInt(3, (Integer) map.get("avg_heartbeats"));
            st.setInt(4, (Integer) map.get("calories"));
            st.setDouble(5, (Double) map.get("elevation"));
            st.setInt(6, (Integer) map.get("floors"));
            st.setInt(7, (Integer) map.get("steps"));
            st.setDouble(8, (Double) map.get("distance"));
            st.setInt(9, (Integer) map.get("minutes_sleep"));
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
