package server.database.v2;

import server.api.v2.Regex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HueDB {

    static Connection conn;

    static private List<Map<String, Object>> getListHue(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, Object> map;

        while(rs.next()) {
            map = new LinkedHashMap<>();
            map.put("patient_id", rs.getInt("patient_id"));
            map.put("timedate", rs.getString("timedate"));
            map.put("chromotherapy", rs.getString("chromotherapy"));
            list.add(map);
        }
        conn.close();
        return list;
    }

    static public List<Map<String, Object>> selectDate(int patientId, String date) {

        final String sql = "SELECT * FROM Hue WHERE patient_id=? AND timedate LIKE ?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date + "%");

            return getListHue(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> selectDateInterval(int patientId, String startTimedate, String endTimedate) {

        final String sql = "SELECT * FROM Hue WHERE patient_id=? AND timedate BETWEEN ? AND ?";

        if(startTimedate.matches(Regex.DATE_REGEX))
            startTimedate += "T00:00:00";
        if(endTimedate.matches(Regex.DATE_REGEX))
            endTimedate += "T23:59:59";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, startTimedate.replace("T", " "));
            st.setString(3, endTimedate.replace("T", " "));

            return getListHue(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> select(int patientId) {

        final String sql = "SELECT * FROM Hue WHERE patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);

            return getListHue(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    static public boolean insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Hue(patient_id, timedate, chromotherapy) VALUES (?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("patient_id"));
            st.setString(2, String.valueOf(map.get("timedate")));
            st.setString(3,  String.valueOf(map.get("chromotherapy")));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static public Map<String, Object> selectTotal(int patientId, String date) {
        final String sql = "SELECT COUNT(chromotherapy) AS total FROM Hue WHERE patient_id=? AND timedate LIKE ? AND chromotherapy=?";
        List<String> chromotherapyList = ChromotherapyDB.select();

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date + "%");
            Map<String, Object> map = new LinkedHashMap<>();

            for(String chromotherapy: chromotherapyList) {
                st.setString(3, chromotherapy);
                ResultSet rs = st.executeQuery();
                map.put("date", date);

                rs.next();
                map.put(chromotherapy, rs.getInt("total"));

            }
            conn.close();
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
