package server.database2;

import server.database.DBConnect;

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
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            List<Map<String, Object>> list = new ArrayList<>();
            LinkedHashMap<String, Object> map;

            while(rs.next()) {
                map = new LinkedHashMap<>();
                map.put("medic_id", rs.getInt("Medic_id"));
                map.put("patient_id", rs.getInt("Patient_id"));
                map.put("timedate", rs.getString("timedate"));
                map.put("medic_sender", rs.getBoolean("medic_sender"));
                map.put("text", rs.getString("message"));
                list.add(map);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Map<String, Object>> selectPatientReceived(int Patient_id, String medic_id, String startdate, String enddate) {
        final String base = "SELECT * FROM Message_Medic_Patient WHERE Patient_id=? AND medic_sender=1";

        StringBuilder sql = new StringBuilder();
        sql.append(base);

        if(medic_id != null)
            sql.append(" AND Medic_id=" + medic_id);

        if(startdate != null && enddate == null)
            sql.append(" AND timedate BETWEEN " + "\'" + startdate + " 00:00:00\' AND \'" + startdate +" 23:59:59\'");
        else if(startdate != null && enddate != null)
            sql.append(" AND timedate BETWEEN " + "\'" + startdate + " 00:00:00\' AND \'" + enddate + " 23:59:59\'");

        return getListMessage(sql.toString(), Patient_id);
    }

    static public List<Map<String, Object>> selectPatientSent(int Patient_id, String medic_id, String startdate, String enddate) {
        String base = "SELECT * FROM Message_Medic_Patient WHERE Patient_id=? AND medic_sender=0";
        StringBuilder sql = new StringBuilder();
        sql.append(base);

        if(medic_id != null)
            sql.append(" AND Medic_id=" + medic_id);

        if(startdate != null && enddate == null)
            sql.append(" AND timedate BETWEEN " + "\'" + startdate + " 00:00:00\' AND \'" + startdate +" 23:59:59\'");
        else if(startdate != null && enddate != null)
            sql.append(" AND timedate BETWEEN " + "\'" + startdate + " 00:00:00\' AND \'" + enddate + "23:59:59\'");

        return getListMessage(sql.toString(), Patient_id);
    }

    static public List<Map<String, Object>> selectMedicReceived(int medicId, String patientId, String ... date) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Message_Medic_Patient WHERE Medic_id=? AND medic_sender=0");

        if(patientId != null)
            sql.append(" AND Patient_id=" + patientId);
        else if(date.length == 1)
            sql.append(" AND timedate LIKE \'" + date[0] + "%\'");
        else if(date.length == 2)
            sql.append(" AND timedate BETWEEN \'" + date[0] + " 00:00:00\' AND \'" + date[1] + " 23:59:59\'");

        return getListMessage(sql.toString(), medicId);
    }

    static public List<Map<String, Object>> selectMedicSent(int medicId, String patientId, String ... date) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Message_Medic_Patient WHERE Medic_id=? AND medic_sender=1");

        if(patientId != null)
            sql.append(" AND Patient_id=" + patientId);
        else if(date.length == 1)
            sql.append(" AND timedate LIKE \'" + date[0] + "%\'");
        else if(date.length == 2)
            sql.append(" AND timedate BETWEEN \'" + date[0] + " 00:00:00\' AND \'" + date[1] + " 23:59:59\'");

        return getListMessage(sql.toString(), medicId);
    }


    static public boolean insert(Map<String, Object> map) {
        final String sql = "INSERT INTO Message_Medic_Patient(Medic_id, Patient_id, timedate, medic_sender, message) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
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

    public static Map<String, Object> selectSingleMessage(int patientId, int medic_id, String timedate) {
        final String sql = "SELECT * FROM Message_Medic_Patient WHERE Patient_id=? AND Medic_id=? AND timedate=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setInt(2, medic_id);
            st.setString(3, timedate);
            ResultSet rs = st.executeQuery();

            rs.next();
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("patien_id", rs.getInt("Patient_id"));
            map.put("medic_id", rs.getInt("Medic_id"));
            map.put("timedate", rs.getString("timedate"));
            map.put("medic_sender", rs.getBoolean("medic_sender"));
            map.put("text", rs.getString("message"));

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
