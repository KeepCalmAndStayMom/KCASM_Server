package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MedicDB {

    static Connection conn;

    static public Map<String, Object> Select(int id) {
        final String sql = "SELECT * FROM Medic WHERE id=?";
        try {
            conn = DBConnect2.getInstance().getConnection();
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

    static public boolean Update(Map<String, Object> map) {
        final String sql = "UPDATE Medic SET name=?, surname=?, age=?, phone=?, specialization=? WHERE id=?";

        try {
            conn = DBConnect2.getInstance().getConnection();
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

    static public boolean Insert(Map<String, Object> map) {
        final String sql = "INSERT INTO Medic(id, name, surname, age, phone, specialization) VALUES (null, ?, ?, ?, ?, ?)";

        try {
            conn = DBConnect2.getInstance().getConnection();
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

    static public boolean Delete(Map<String, Object> map) {
        final String sql = "DELETE from Medic WHERE id=?";

        try {
            conn = DBConnect2.getInstance().getConnection();
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
}