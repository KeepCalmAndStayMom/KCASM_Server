package server.database2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class SharedTaskFunctionDB {

    static Connection conn;

    private static List<Map<String,Object>> getListTask(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, Object> map;

        while(rs.next()) {
            map = new LinkedHashMap<>();
            map.put("id", rs.getInt("id"));
            map.put("patient_id", rs.getInt("Patient_id"));
            map.put("medic_id", rs.getInt("Medic_id"));
            map.put("date", rs.getString("date"));
            map.put("category", rs.getString("category"));
            map.put("description", rs.getString("description"));
            map.put("starting_program", rs.getBoolean("starting_program"));
            map.put("executed", rs.getBoolean("executed"));
            list.add(map);
        }

        return list;
    }

    static List<Map<String,Object>> getTaskPatientId(String sql, int patientId) {

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);


            return getListTask(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static List<Map<String, Object>> getTaskPatientIdDate(String sql, int patientId, String date)
    {
        try {
            Connection conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, date);

            return SharedTaskFunctionDB.getListTask(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static boolean executeUpdate(String sql, Map<String, Object> map)
    {
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("Patient_id"));
            st.setString(2, String.valueOf(map.get("date")));
            st.setString(3, String.valueOf(map.get("category")));
            st.setString(4, String.valueOf(map.get("description")));
            st.setBoolean(5, (Boolean) map.get("starting_program"));
            st.setBoolean(6, (Boolean) map.get("executed"));
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

    static boolean executeInsert(String sql, Map<String,Object> map)
    {
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("Patient_id"));
            st.setInt(2, (Integer) map.get("Medic_id"));
            st.setString(3, String.valueOf(map.get("date")));
            st.setString(4, String.valueOf(map.get("category")));
            st.setString(5, String.valueOf(map.get("description")));
            st.setBoolean(6, (Boolean) map.get("starting_program"));
            st.setBoolean(7, (Boolean) map.get("executed"));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean executeDelete(String sql, int id)
    {
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            st.setInt(1, id);

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

    public static List<Map<String, Object>> getTaskPatientIdDateInterval(String sql, int patientId, String startdate, String enddate) {

        try {
            Connection conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, patientId);
            st.setString(2, startdate);
            st.setString(3, enddate);

            return SharedTaskFunctionDB.getListTask(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
