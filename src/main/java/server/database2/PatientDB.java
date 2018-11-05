package server.database2;

import server.MainServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PatientDB {

    static Connection conn;

    static public Map<String, Object> select(int id) {
        final String sql = "SELECT * FROM Patient WHERE id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            rs.next();
            map.put("name", rs.getString("name"));
            map.put("surname", rs.getString("surname"));
            map.put("age", rs.getInt("age"));
            map.put("phone", rs.getString("phone"));
            map.put("address_home", rs.getString("address_home"));
            map.put("address_hospital", rs.getString("address_hospital"));

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean update(Map<String, Object> map) {
        final String sql = "UPDATE Patient SET name=?, surname=?, age=?, phone=?, address_home=?, address_hospital=? WHERE id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("name")));
            st.setString(2, String.valueOf(map.get("surname")));
            st.setInt(3, ((Double) map.get("age")).intValue());
            st.setString(4, String.valueOf(map.get("phone")));
            st.setString(5, String.valueOf(map.get("address_home")));
            st.setString(6, String.valueOf(map.get("address_hospital")));
            st.setInt(7, (Integer) map.get("id"));


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
        final String sql = "INSERT INTO Patient(name, surname, age, phone, address_home, address_hospital) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("name")));
            st.setString(2, String.valueOf(map.get("surname")));
            st.setInt(3, ((Double) map.get("age")).intValue());
            st.setString(4, String.valueOf(map.get("phone")));
            st.setString(5, String.valueOf(map.get("address_home")));
            st.setString(6, String.valueOf(map.get("address_hospital")));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static public boolean delete(int patientId) {
        final String sql = "DELETE from Patient WHERE id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            if(st.executeUpdate() != 0) {
                conn.close();
                MainServer.cpt.removeID(patientId);
                return true;
            }

            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Map<String, Object>> selectMedicsOfPatient(int id) {
        final String sql = "SELECT Medic.id, Medic.name, Medic.surname, Medic.specialization FROM Medic JOIN Medic_has_Patient ON Medic.id=medic_id WHERE patient_id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            List<Map<String, Object>> list = new ArrayList<>();

            while(rs.next()) {
                Map<String, Object> medic = new LinkedHashMap<>();
                medic.put("id", rs.getInt("id"));
                medic.put("name", rs.getString("name"));
                medic.put("surname", rs.getString("surname"));
                medic.put("specialization", rs.getString("specialization"));

                list.add(medic);
            }

            return list;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
