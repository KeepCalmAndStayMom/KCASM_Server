package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SensorDB {

    static Connection conn;

    static private LinkedList<LinkedHashMap<String, Object>> getListSensor(ResultSet rs) throws SQLException {
        LinkedList<LinkedHashMap<String, Object>> list = new LinkedList<>();
        LinkedHashMap<String, Object> map;

        while(rs.next()) {
            map = new LinkedHashMap<>();
            map.put("Patient_id", rs.getInt("Patient_id"));
            map.put("timedate", rs.getString("timedate"));
            map.put("temperature", rs.getDouble("temperature"));
            map.put("luminescence", rs.getDouble("luminescence"));
            map.put("humidity", rs.getDouble("humidity"));
            list.add(map);
        }

        return list;
    }

    static public LinkedList<LinkedHashMap<String, Object>> selectDate(int patientId, String date) {

        final String sql = "SELECT * FROM Sensor WHERE Patient_id=? AND timedate LIKE ?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date);

            return getListSensor(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public LinkedList<LinkedHashMap<String, Object>> selectDateInterval(int patientId, String startTimedate, String endTimedate) {

        final String sql = "SELECT * FROM Sensor WHERE Patient_id=? AND timedate BETWEEN ? AND ?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, startTimedate);
            st.setString(3, endTimedate);

            return getListSensor(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public LinkedList<LinkedHashMap<String, Object>> select(String patientId) {

        final String sql = "SELECT * FROM Sensor WHERE Patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, patientId);

            return getListSensor(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    static public boolean insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Sensor(Patient_id, timedate, temperature, luminescence, humidity) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("Patient_id"));
            st.setString(2, String.valueOf(map.get("timedate")));
            st.setDouble(3,  (Double) map.get("temperature"));
            st.setDouble(4,  (Double) map.get("luminescence"));
            st.setDouble(5,  (Double) map.get("humidity"));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
