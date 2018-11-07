package server.database2;

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

    static public Map<String, Integer> selectTotal(int patientId, String date) {
        final String sql = "SELECT COUNT(chromotherapy) AS total FROM Hue WHERE patient_id=? AND timedate LIKE ? AND chromotherapy=?";
        List<String> bmiList = BMIDB.select();

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date + "%");
            Map<String, Integer> map = new LinkedHashMap<>();

            for(String bmi: bmiList) {
                st.setString(3, bmi);
                ResultSet rs = st.executeQuery();

                if(rs.next())
                    map.put(bmi, rs.getInt("total"));
                else
                    map.put(bmi, 0);

            }

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
