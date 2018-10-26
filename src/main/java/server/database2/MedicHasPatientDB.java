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

    static public List<Integer> SelectPatients(int Medic_id) {

        final String sql = "SELECT * FROM Medic_has_Patient WHERE Medic_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, Medic_id);
            ResultSet rs = st.executeQuery();

            List<Integer> list = new ArrayList<>();

            while(rs.next()) {
                list.add(rs.getInt("Patient_id"));
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public List<Integer> SelectMedics(int Patient_id) {

        final String sql = "SELECT * FROM Medic_has_Patient WHERE Patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, Patient_id);
            ResultSet rs = st.executeQuery();

            List<Integer> list = new ArrayList<>();

            while(rs.next()) {
                list.add(rs.getInt("Medic_id"));
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean Insert(Map<String, Object> map) {

        final String sql = "INSERT INTO Medic_has_Patient(Medic_id, Patient_id) VALUES (?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("Medic_id"));
            st.setInt(2, (Integer) map.get("Patient_id"));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
