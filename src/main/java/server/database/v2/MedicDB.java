package server.database.v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MedicDB {

    static Connection conn;

    static public Map<String, Object> select(int id) {
        final String sql = "SELECT * FROM Medic WHERE id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            LinkedHashMap<String, Object> map = null;

            if(rs.next()) {
                map = new LinkedHashMap<>();
                map.put("name", rs.getString("name"));
                map.put("surname", rs.getString("surname"));
                map.put("age", rs.getInt("age"));
                map.put("phone", rs.getString("phone"));
                map.put("specialization", rs.getString("specialization"));
                map.put("email_notify", rs.getBoolean("email_notify"));
                map.put("sms_notify", rs.getBoolean("sms_notify"));
                map.put("address", rs.getString("address"));
            }

            conn.close();
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean update(Map<String, Object> map) {
        final String sql = "UPDATE Medic SET name=?, surname=?, age=?, phone=?, email_notify=?, sms_notify=? , address=? WHERE id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("name")));
            st.setString(2, String.valueOf(map.get("surname")));
            st.setInt(3, ((Double) map.get("age")).intValue());
            st.setString(4, String.valueOf(map.get("phone")));
            st.setBoolean(5, (boolean) map.get("email_notify"));
            st.setBoolean(6, (boolean) map.get("sms_notify"));
            st.setString(7, String.valueOf(map.get("address")));
            st.setInt(8, (Integer) map.get("id"));


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
        final String sql = "INSERT INTO Medic(name, surname, age, phone, address, specialization, email_notify, sms_notify) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("name")));
            st.setString(2, String.valueOf(map.get("surname")));
            st.setInt(3, ((Double) map.get("age")).intValue());
            st.setString(4, String.valueOf(map.get("phone")));
            st.setString(5, String.valueOf(map.get("address")));
            st.setString(6, String.valueOf(map.get("specialization")));
            st.setBoolean(7, (boolean) map.get("email_notify"));
            st.setBoolean(8, (boolean) map.get("sms_notify"));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static public boolean delete(int medicId) {
        final String sql = "DELETE from Medic WHERE id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, medicId);
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

    public static List<Map<String, Object>> selectPatientsOfMedic(int id) {
        final String sql = "SELECT Patient.id, Patient.name, Patient.surname FROM Patient JOIN Medic_has_Patient ON Patient.id=patient_id WHERE medic_id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            List<Map<String, Object>> list = new ArrayList<>();

            while(rs.next()) {
                Map<String, Object> patient = new LinkedHashMap<>();
                patient.put("id", rs.getInt("id"));
                patient.put("name", rs.getString("name"));
                patient.put("surname", rs.getString("surname"));

                list.add(patient);
            }
            conn.close();
            return list;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
