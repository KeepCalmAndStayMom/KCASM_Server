package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MedicHasPatientDB {

    static Connection conn;

    static public List<Integer> selectPatients(int Medic_id) {

        final String sql = "SELECT * FROM Medic_has_Patient WHERE medic_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, Medic_id);
            ResultSet rs = st.executeQuery();

            List<Integer> list = new ArrayList<>();

            while(rs.next()) {
                list.add(rs.getInt("patient_id"));
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Integer> selectMedics(int Patient_id) {

        final String sql = "SELECT * FROM Medic_has_Patient WHERE patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, Patient_id);
            ResultSet rs = st.executeQuery();

            List<Integer> list = new ArrayList<>();

            while(rs.next()) {
                list.add(rs.getInt("medic_id"));
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Medic_has_Patient(medic_id, patient_id) VALUES (?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("medic_id"));
            st.setInt(2, (Integer) map.get("patient_id"));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
