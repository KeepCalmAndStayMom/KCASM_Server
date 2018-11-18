package server.database.v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SensorDB {

    static Connection conn;

    static private List<Map<String, Object>> getListSensor(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, Object> map;

        while(rs.next()) {
            map = new LinkedHashMap<>();
            map.put("patient_id", rs.getInt("patient_id"));
            map.put("timedate", rs.getString("timedate"));
            map.put("temperature", rs.getDouble("temperature"));
            map.put("luminescence", rs.getDouble("luminescence"));
            map.put("humidity", rs.getDouble("humidity"));
            list.add(map);
        }
        conn.close();
        return list;
    }

    static public List<Map<String, Object>> selectDate(int patientId, String date) {

        final String sql = "SELECT * FROM Sensor WHERE patient_id=? AND timedate LIKE ?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date + "%");

            return getListSensor(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> selectDateInterval(int patientId, String startTimedate, String endTimedate) {

        final String sql = "SELECT * FROM Sensor WHERE patient_id=? AND timedate BETWEEN ? AND ?";

        if(startTimedate.length()==10)
            startTimedate += "T00:00:00";
        if(endTimedate.length()==10)
            endTimedate += "T23:59:59";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, startTimedate.replace("T", " "));
            st.setString(3, endTimedate.replace("T", " "));

            return getListSensor(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> select(int patientId) {

        final String sql = "SELECT * FROM Sensor WHERE patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);

            return getListSensor(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    static public boolean insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Sensor(patient_id, timedate, temperature, luminescence, humidity) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("patient_id"));
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

    public static Map<String, Object> selectTotal(int patientId, String date) {
        final String sql = "SELECT AVG(temperature) AS temperature, AVG(luminescence) AS luminescence, AVG(humidity) AS humidity FROM Sensor WHERE patient_id=? AND timedate LIKE ?";


        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date + "%");
            ResultSet rs = st.executeQuery();
            Map map = new LinkedHashMap();

            rs.next();
            map.put("temperature", rs.getDouble("temperature"));
            map.put("luminescence", rs.getDouble("luminescence"));
            map.put("humidity", rs.getDouble("humidity"));
            conn.close();
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
