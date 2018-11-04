package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PatientInitialDB {

    static Connection conn;

    static public Map<String, Object> select(int id) {
        final String sql = "SELECT * FROM Patient_Initial WHERE patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            rs.next();
            map.put("pregnancy_start_date", rs.getString("pregnancy_start_date"));
            map.put("weight", rs.getDouble("weight"));
            map.put("height", rs.getDouble("height"));
            map.put("bmi", rs.getString("bmi"));
            map.put("twin", rs.getBoolean("twin"));

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean update(Map<String, Object> map) {
        final String sql = "UPDATE Patient_Initial SET twin=? WHERE patient_id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setBoolean(1, (Boolean) map.get("twin"));
            st.setInt(2, (Integer) map.get("patient_id"));

            if(st.executeUpdate() != 0) {
                conn.close();
                return true;
            }
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    static public boolean insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Patient_Initial(pregnancy_start_date, weight, height, bmi, twin, patient_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("pregnancy_start_date")));
            st.setDouble(2, (Double) map.get("weight"));
            st.setDouble(3, (Double) map.get("height"));
            st.setString(4, String.valueOf(map.get("bmi")));
            st.setBoolean(5, (Boolean) map.get("twin"));
            st.setInt(6, (int) map.get("patient_id"));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
