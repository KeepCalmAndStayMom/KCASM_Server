package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.sql.Types;

public class LoginDB {

    static Connection conn;

    static public Map<String, Object> selectPatient(int patientId) {
        final String sql = "SELECT * FROM Login WHERE patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            ResultSet rs = st.executeQuery();

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            rs.next();
            map.put("patient_id", rs.getInt("patient_id"));
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
        final String sql = "SELECT * FROM Login WHERE medic_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, medicId);
            ResultSet rs = st.executeQuery();

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            rs.next();
            map.put("medic_id", rs.getInt("medic_id"));
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
        final String sql = "UPDATE Login SET email=?, password=?, email_notify=?, sms_notify=? WHERE patient_id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("email")));
            st.setString(2, String.valueOf(map.get("password")));
            st.setInt(3, (Integer) map.get("patient_id"));
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
        final String sql = "UPDATE Login SET email=?, password=?, email_notify=?, sms_notify=? WHERE medic_id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("email")));
            st.setString(2, String.valueOf(map.get("password")));
            st.setInt(3, (Integer) map.get("medic_id"));
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
        final String sql = "INSERT INTO Login(email, password, patient_id, medic_id, email_notify, sms_notify) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("email")));
            st.setString(2, String.valueOf(map.get("password")));

            if(map.get("patient_id") != null)
                st.setInt(3, (int) map.get("patient_id"));
            else
                st.setNull(3, Types.INTEGER);

            if(map.get("medic_id") != null)
                st.setInt(4, (int) map.get("medic_id"));
            else
                st.setNull(4, Types.INTEGER);

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
