package server.database2;

import server.MainServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WeightDB {

    static Connection conn;

    static public Map<String, Double> select(int id) {

        final String sql = "SELECT * FROM Weight WHERE patient_id=?";
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

    public static List<Map<String, Object>> selectList(int id) {

        final String sql = "SELECT * FROM Weight WHERE patient_id=?";
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map;

            while(rs.next()) {
                map = new LinkedHashMap<>();
                map.put("patient_id", rs.getInt("patient_id"));
                map.put("date", rs.getString("date"));
                map.put("weight", rs.getString("weight"));

                list.add(map);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> selectSingleWeight(int id, String date) {
        final String sql = "SELECT * FROM Weight WHERE patient_id=? AND date=?";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.setString(2, date);
            ResultSet rs = st.executeQuery();

            Map<String, Object> map = new LinkedHashMap<>();

            rs.next();
            map.put("patient_id", rs.getInt("patient_id"));
            map.put("date", rs.getString("date"));
            map.put("weight", rs.getString("weight"));

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public boolean update(Map<String, Object> map) {
        final String sql = "UPDATE Weight SET weight=? WHERE patient_id=? AND 'date'=?";

        Map<String, Double> pesi = WeightDB.select((Integer) map.get("Patient_id"));
        assert pesi != null;
        Object[] date = pesi.keySet().toArray();

        if(String.valueOf(map.get("date")).equals(String.valueOf(date.length-1))) {
            try {
                conn = DBConnectOnline.getInstance().getConnection();
                PreparedStatement st = conn.prepareStatement(sql);
                st.setDouble(1, (Double) map.get("weight"));
                st.setInt(2, (Integer) map.get("patient_id"));
                st.setString(3, String.valueOf(map.get("date")));


                if (st.executeUpdate() != 0) {
                    conn.close();
                    MainServer.cpt.startcheck((Integer) map.get("patient_id"), LocalDate.parse(String.valueOf(map.get("date"))), (Double) map.get("weight"));
                    return true;
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return false;
    }

    static public boolean insert(Map<String, Object> map) {
        final String sql = "INSERT INTO Weight(patient_id, date, weight) VALUES (?, ?, ?)";

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("patient_id"));
            st.setString(2, String.valueOf(map.get("date")));
            st.setDouble(3, (Double) map.get("weight"));
            st.executeUpdate();
            conn.close();

            MainServer.cpt.startcheck((Integer) map.get("patient_id"), LocalDate.parse(String.valueOf(map.get("date"))), (Double) map.get("weight"));

            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
