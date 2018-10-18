package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PatientInitialDB implements ClassDB{

    Connection conn;

    @Override
    public List<Map<String, Object>> Select(int id) {
        final String sql = "SELECT * FROM Patient_Initial WHERE Patient_id=?";
        try {
            conn = DBConnect2.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            List<Map<String, Object>> list = new ArrayList<>();
            LinkedHashMap<String, Object> map;

            while(rs.next()) {
                map = new LinkedHashMap<>();
                map.put("pregnancy_start_date", rs.getString("pregnancy_start_date"));
                map.put("weight", rs.getDouble("weight"));
                map.put("height", rs.getDouble("height"));
                map.put("bmi", rs.getString("bmi"));
                map.put("twin", rs.getBoolean("twin"));
                list.add(map);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean Update(Map<String, Object> map) {
        final String sql = "UPDATE Patient_Initial SET twin=? WHERE Patient_id=?";

        try {
            conn = DBConnect2.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setBoolean(1, (Boolean) map.get("twin"));
            st.setInt(2, (Integer) map.get("Patient_id"));

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

    @Override
    public boolean Insert(Map<String, Object> map) {
        return false;
    }

    /*NON NECESSARIA*/
    @Override
    public boolean Delete(Map<String, Object> map) {
        return false;
    }
}
