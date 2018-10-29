package server.database2;

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

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            rs.next();
            map.put("name", rs.getString("name"));
            map.put("surname", rs.getString("surname"));
            map.put("age", rs.getInt("age"));
            map.put("phone", rs.getString("phone"));
            map.put("specialization", rs.getString("specialization"));

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean update(Map<String, Object> map) {
        final String sql = "UPDATE Medic SET name=?, surname=?, age=?, phone=?, specialization=? WHERE id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("name")));
            st.setString(2, String.valueOf(map.get("surname")));
            st.setInt(3, (Integer) map.get("age"));
            st.setString(4, String.valueOf(map.get("phone")));
            st.setString(5, String.valueOf(map.get("specialization")));
            st.setInt(6, (Integer) map.get("id"));


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
        final String sql = "INSERT INTO Medic(id, name, surname, age, phone, specialization) VALUES (null, ?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, String.valueOf(map.get("name")));
            st.setString(2, String.valueOf(map.get("surname")));
            st.setInt(3, (Integer) map.get("age"));
            st.setString(4, String.valueOf(map.get("phone")));
            st.setString(5, String.valueOf(map.get("specialization")));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static public boolean delete(Map<String, Object> map) {
        final String sql = "DELETE from Medic WHERE id=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("id"));
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

    public static List<Map<String, Object>> selectMedicsOfPatient(int id) {
        final String sql = "SELECT Medic.id, Medic.name, Medic.surname, Medic.specialization FROM Medic JOIN Medic_has_Patient ON Medic.id=Medic_id WHERE Patient_id=?";

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
