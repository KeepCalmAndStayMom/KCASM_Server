package server.database.v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

            LinkedHashMap<String, Object> map = null;

            if(rs.next()) {
                map = new LinkedHashMap<>();
                map.put("patient_id", rs.getInt("patient_id"));
                map.put("email", rs.getString("email"));
                map.put("password", rs.getString("password"));
            }

            conn.close();
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

            LinkedHashMap<String, Object> map = null;

            if(rs.next()) {
                map = new LinkedHashMap<>();
                map.put("medic_id", rs.getInt("medic_id"));
                map.put("email", rs.getString("email"));
                map.put("password", rs.getString("password"));
            }

            conn.close();
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean updatePatient(Map<String, Object> map) {
        final String sql = "UPDATE Login SET email=?, password=? WHERE patient_id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("email")));
            st.setString(2, String.valueOf(map.get("password")));
            st.setInt(3, (Integer) map.get("patient_id"));

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
        final String sql = "UPDATE Login SET email=?, password=? WHERE medic_id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("email")));
            st.setString(2, String.valueOf(map.get("password")));
            st.setInt(3, (Integer) map.get("medic_id"));

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
        final String sql = "INSERT INTO Login(email, password, patient_id, medic_id) VALUES (?, ?, ?, ?)";

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

            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Map<String, Integer> selectLogin(String email, String password) {
        final String sql = "SELECT patient_id, medic_id FROM Login WHERE email=? AND password=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            Map map = null;

            if(rs.next()) {
                map = new LinkedHashMap();

                if(rs.getObject("patient_id")!=null)
                    map.put("patient_id", rs.getInt("patient_id"));
                else
                    map.put("medic_id", rs.getInt("medic_id"));
            }

            conn.close();
            return map;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, String> selectForPasswordReset(String email) {
        final String sql = "SELECT password FROM Login WHERE email=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            Map map = null;

            if(rs.next()) {
                map = new HashMap();
                map.put("password", rs.getString("password"));
            }

            conn.close();
            return map;

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
