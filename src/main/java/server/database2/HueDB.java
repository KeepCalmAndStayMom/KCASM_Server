package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HueDB {

    static Connection conn;

    static private List<Map<String, Object>> getListHue(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, Object> map;

        while(rs.next()) {
            map = new LinkedHashMap<>();
            map.put("Patient_id", rs.getInt("Patient_id"));
            map.put("timedate", rs.getString("timedate"));
            map.put("cromoterapia", rs.getString("cromoterapia"));
            list.add(map);
        }

        return list;
    }

    static public List<Map<String, Object>> SelectDate(int patientId, String date) {

        final String sql = "SELECT * FROM Hue WHERE Patient_id=? AND timedate LIKE ?";
        try {
            conn = DBConnect2.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date);

            return getListHue(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> SelectDateInterval(int patientId, String start_timedate, String end_timedate) {

        final String sql = "SELECT * FROM Hue WHERE Patient_id=? AND timedate BETWEEN ? AND ?";
        try {
            conn = DBConnect2.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, start_timedate);
            st.setString(3, end_timedate);

            return getListHue(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> Select(int patientId) {

        final String sql = "SELECT * FROM Hue WHERE Patient_id=?";
        try {
            conn = DBConnect2.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);

            return getListHue(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    static public boolean Insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Hue(Patient_id, timedate, cromoterapia) VALUES (?, ?, ?)";

        try {
            conn = DBConnect2.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("Patient_id"));
            st.setString(2, String.valueOf(map.get("timedate")));
            st.setString(3,  String.valueOf(map.get("cromoterapia")));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
