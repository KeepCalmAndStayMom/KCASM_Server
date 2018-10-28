package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginDB {

    static Connection conn;

    static public Map<String, Object> selectPatient(int patientId) {
        final String sql = "SELECT * FROM Login WHERE Patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            ResultSet rs = st.executeQuery();

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            rs.next();
            map.put("Patient_id", rs.getInt("Patient_id"));
            map.put("email", rs.getString("email"));
            map.put("password", rs.getString("password"));
            map.put("email_notify", rs.getBoolean("email_notify"));
            map.put("sms_notify", rs.getBoolean("sms_notify"));

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public Map<String, Object> selectMedic(int medicId) {
        final String sql = "SELECT * FROM Login WHERE Medic_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, medicId);
            ResultSet rs = st.executeQuery();

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            rs.next();
            map.put("Medic_id", rs.getInt("Medic_id"));
            map.put("email", rs.getString("email"));
            map.put("password", rs.getString("password"));
            map.put("email_notify", rs.getBoolean("email_notify"));
            map.put("sms_notify", rs.getBoolean("sms_notify"));

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean updatePatient(Map<String, Object> map) {
        final String sql = "UPDATE Login SET email=?, password=?, email_notify=?, sms_notify=? WHERE Patient_id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("email")));
            st.setString(2, String.valueOf(map.get("password")));
            st.setInt(3, (Integer) map.get("Patient_id"));
            st.setBoolean(4, (Boolean) map.get("email_notify"));
            st.setBoolean(4, (Boolean) map.get("sms_notify"));

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

    static public boolean updateMedic(Map<String, Object> map) {
        final String sql = "UPDATE Login SET email=?, password=?, email_notify=?, sms_notify=? WHERE Medic_id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("email")));
            st.setString(2, String.valueOf(map.get("password")));
            st.setInt(3, (Integer) map.get("Medic_id"));
            st.setBoolean(4, (Boolean) map.get("email_notify"));
            st.setBoolean(4, (Boolean) map.get("sms_notify"));

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
        final String sql = "INSERT INTO Login(email, password, Patient_id, Medic_id, email_notify, sms_notify) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("email")));
            st.setString(2, String.valueOf(map.get("password")));
            st.setInt(3, (Integer) map.get("Patient_id"));
            st.setInt(4, (Integer) map.get("Medic_id"));
            st.setBoolean(5, (Boolean) map.get("email_notify"));
            st.setBoolean(6, (Boolean) map.get("sms_notify"));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
