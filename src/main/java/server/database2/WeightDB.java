package server.database2;

import server.MainServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeightDB {

    static Connection conn;

    static public Map<String, Double> Select(int id) {

        final String sql = "SELECT * FROM Weight WHERE Patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            LinkedHashMap<String, Double> map = new LinkedHashMap<>();

            while(rs.next()) {
                map.put(rs.getString("date"), rs.getDouble("weight"));
            }

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean Update(Map<String, Object> map) {
        final String sql = "UPDATE Weight SET weight=? WHERE Patient_id=? AND 'date'=?";

        Map<String, Double> pesi = WeightDB.Select((Integer) map.get("Patient_id"));
        assert pesi != null;
        Object[] date = pesi.keySet().toArray();

        if(String.valueOf(map.get("date")).equals(String.valueOf(date.length-1))) {
            try {
                conn = DBConnectOnline.getInstance().getConnection();
                PreparedStatement st = conn.prepareStatement(sql);
                st.setDouble(1, (Double) map.get("weight"));
                st.setInt(2, (Integer) map.get("Patient_id"));
                st.setString(3, String.valueOf(map.get("date")));


                if (st.executeUpdate() != 0) {
                    conn.close();
                    MainServer.cpt.startcheck((Integer) map.get("Patient_id"), LocalDate.parse(String.valueOf(map.get("date"))), (Double) map.get("weight"));
                    return true;
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return false;
    }

    static public boolean Insert(Map<String, Object> map) {
        final String sql = "INSERT INTO Weight(Patient_id, date, weight) VALUES (?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("Patient_id"));
            st.setString(2, String.valueOf(map.get("date")));
            st.setDouble(3, (Double) map.get("weight"));
            st.executeUpdate();
            conn.close();

            MainServer.cpt.startcheck((Integer) map.get("Patient_id"), LocalDate.parse(String.valueOf(map.get("date"))), (Double) map.get("weight"));

            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
