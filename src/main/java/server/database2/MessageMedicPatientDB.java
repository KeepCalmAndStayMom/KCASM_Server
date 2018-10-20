package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MessageMedicPatientDB {

    static Connection conn;

    static private List<Map<String, Object>> getListMessage(String sql, int id) {
        try {
            conn = DBConnect2.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            List<Map<String, Object>> list = new ArrayList<>();
            LinkedHashMap<String, Object> map;

            while(rs.next()) {
                map = new LinkedHashMap<>();
                map.put("Medic_id", rs.getInt("Medic_id"));
                map.put("Patient_id", rs.getInt("Patient_id"));
                map.put("timedate", rs.getString("timedate"));
                map.put("medic_sender", rs.getBoolean("medic_sender"));
                map.put("message", rs.getString("message"));
                list.add(map);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> SelectPatientReceived(int Patient_id) {
        final String sql = "SELECT * FROM Message_Medic_Patient WHERE Patient_id=? AND medic_sender=1";

        return getListMessage(sql, Patient_id);
    }

    static public List<Map<String, Object>> SelectPatientSent(int Patient_id) {

        final String sql = "SELECT * FROM Message_Medic_Patient WHERE Patient_id=? AND medic_sender=0";

        return getListMessage(sql, Patient_id);
    }

    static public List<Map<String, Object>> SelectMedicReceived(int Medic_id) {

        final String sql = "SELECT * FROM Message_Medic_Patient WHERE Medic_id=? AND medic_sender=0";

        return getListMessage(sql, Medic_id);
    }

    static public List<Map<String, Object>> SelectMedicSent(int Medic_id) {

        final String sql = "SELECT * FROM Message_Medic_Patient WHERE Medic_id=? AND medic_sender=1";

        return getListMessage(sql, Medic_id);
    }


    static public boolean Insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Message_Medic_Patient(Medic_id, Patient_id, timedate, medic_sender, message) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DBConnect2.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("Medic_id"));
            st.setInt(2, (Integer) map.get("Patient_id"));
            st.setString(3, String.valueOf(map.get("timedate")));
            st.setBoolean(4, (Boolean) map.get("medic_sender"));
            st.setString(5, String.valueOf(map.get("message")));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
