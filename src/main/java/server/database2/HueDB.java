package server.database2;

import server.api.v2.Link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HueDB {

    static Connection conn;

    static private LinkedList<LinkedHashMap<String, Object>> getListHue(ResultSet rs) throws SQLException {
        LinkedList<LinkedHashMap<String, Object>> list = new LinkedList<>();
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

    static public LinkedList<LinkedHashMap<String, Object>> selectDate(int patientId, String date) {

        final String sql = "SELECT * FROM Hue WHERE Patient_id=? AND timedate LIKE ?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date);

            return getListHue(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public LinkedList<LinkedHashMap<String, Object>> selectDateInterval(int patientId, String startTimedate, String endTimedate) {

        final String sql = "SELECT * FROM Hue WHERE Patient_id=? AND timedate BETWEEN ? AND ?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, startTimedate);
            st.setString(3, endTimedate);

            return getListHue(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public LinkedList<LinkedHashMap<String, Object>> select(String patientId) {

        final String sql = "SELECT * FROM Hue WHERE Patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, patientId);

            return getListHue(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    static public boolean insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Hue(Patient_id, timedate, cromoterapia) VALUES (?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
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
